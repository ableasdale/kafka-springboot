package com.alexbleasdale.kafkademo;

import com.marklogic.client.document.DocumentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.error.Mark;

import java.lang.invoke.MethodHandles;

@SpringBootApplication
public class KafkaDemoApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static void main(String[] args) {

        /*LOG.info("starting");
        MarkLogicClient.getInstance();
        LOG.info("DB: "+MarkLogicClient.getInstance().getDatabase());
        MarkLogicClient.writeADoc();*/
       // DocumentManager xmlDocMgr = MarkLogicClient.getInstance().newXMLDocumentManager();
       // xmlDocMgr.writeAs("/test12345678.xml", "<fromkafka/>");


        SpringApplication.run(KafkaDemoApplication.class, args);
    }

}
