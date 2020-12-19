package com.alexbleasdale.kafkademo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private KafkaTemplate<String, Model> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, Model> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void post(@RequestBody Model model){
        kafkaTemplate.send("FirstTopic", model);
    }

    @KafkaListener(topics = "FirstTopic")
    public void getFromKafka(Model model){
        LOG.info("I heard: " + model.toString());
    }

    @Bean
    public Gson jsonConverter(){
        return new Gson();
    }
}
