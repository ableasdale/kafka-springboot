package com.alexbleasdale.kafkademo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Autowired
    public KafkaJsonController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void post(@RequestBody String string) {
        kafkaTemplate.send("marklogic", string);    // jsonConverter.toJson(string));
    }
}
