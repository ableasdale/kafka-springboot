package com.alexbleasdale.kafkademo;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void post(@RequestBody String string) {
        kafkaTemplate.send(KafkaHelper.TOPIC, string);
    }

    @KafkaListener(topics = KafkaHelper.TOPIC)
    public void getFromKafka(String string) {
        LOG.info(String.format("Listener received payload from topic %s", KafkaHelper.TOPIC));
        LOG.debug(String.format("Full String from HTTP POST: %s", string));

        /*
        First test: turn the POSTed String into a proper Java ROME object
        (to prove this can be marshalled into a usable Object at any stage in the process!)
         */

        try {
            InputSource source = new InputSource(new StringReader(string));
            SyndFeed rss = new SyndFeedInput().build(source);
            LOG.info(String.format("Rome has converted the following feed: %s", rss.getTitle()));

            // Second test: can I HTTP POST this to MarkLogic (as a simple test) from inside the Kafka Consumer?
            // on a second note - it's really not obvious whether the java 9 lib supports digest auth! - scrap this
            /*
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8000/v1/documents"))

                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/xml")
                    .POST(HttpRequest.BodyPublishers.ofString(string))///HttpRequest.BodyProcessor.fromString(feedText))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request2, HttpResponse.BodyHandlers.ofString());
            LOG.info(response.toString()); */
        } catch (FeedException e) {
            LOG.error(String.format("Caught an exception: %s", e.getMessage()), e);
        }

        /*
         Second test: can I write this string (as an XML document) to MarkLogic (as a simple test)
         from inside the Kafka Consumer?
         I'm using the MarkLogic Java Client API to handle the string data
         */
        MarkLogicClient.writeXmlDocument(String.format("/%s.xml", java.util.UUID.randomUUID()), string);
    }

    //@Bean
    // public Gson jsonConverter(){
    //  return new Gson();
    //  }
}
