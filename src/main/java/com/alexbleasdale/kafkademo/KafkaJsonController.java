package com.alexbleasdale.kafkademo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/kafka/json")
public class KafkaJsonController {

    private final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private KafkaTemplate<String, String> kafkaTemplate;
    private Gson jsonConverter;

    @Autowired
    public KafkaJsonController(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void post(@RequestBody String string) {
        LOG.info("!!!!! In the post method!");
        kafkaTemplate.send("marklogic", jsonConverter.toJson(string));
    }

    @KafkaListener(topics = "marklogic")
    public void getJsonFromKafka(String string) {
        LOG.info("I heard: " + jsonConverter.fromJson(string, String.class));
    }
}
