package com.alexbleasdale.kafkademo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
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
        LOG.info("I heard: " + string);
    }

    //@Bean
   // public Gson jsonConverter(){
      //  return new Gson();
  //  }
}
