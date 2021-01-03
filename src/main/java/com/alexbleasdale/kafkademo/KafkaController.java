package com.alexbleasdale.kafkademo;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.StringHandle;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;
import com.rometools.rome.io.XmlReader;
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

import java.io.IOException;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// , consumes = MediaType.TEXT_PLAIN
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void post(@RequestBody String string){
        kafkaTemplate.send(KafkaHelper.TOPIC, string);
    }

    @KafkaListener(topics = KafkaHelper.TOPIC)
    public void getFromKafka(String string){
        LOG.info("Listener received payload...");
        //LOG.info("I heard: " + string);

       // SyndFeedInput in = new SyndFeedInput();

        //SyndFeed rss = new SyndFeedInput().build(string);
        //SyndFeed feed = in.build(new XmlReader(string));

        // First test: turn the String into a proper Java ROME object (to prove this will work!)
        try {
            InputSource source = new InputSource(new StringReader(string));
            SyndFeed rss = new SyndFeedInput().build(source);
            LOG.info("Rome has converted the following feed: "+rss.getTitle());

            // Second test: can i post this to MarkLogic (as a simple test) from inside the Kafka Consumer?
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
        } catch (FeedException  e) {
            e.printStackTrace();
        }

        MarkLogicClient.writeADoc("/test-kafka-document-from-post.xml", string);
    }

    //@Bean
   // public Gson jsonConverter(){
      //  return new Gson();
  //  }
}
