package com.alexbleasdale.kafkademo;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class MarkLogicClient {

    //private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static class LazyHolder {
        static final DatabaseClient INSTANCE = DatabaseClientFactory
                .newClient("localhost", 8000, "Documents",
                        new DatabaseClientFactory.DigestAuthContext("admin", "admin")
                );
    }

    public static DatabaseClient getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void writeADoc(String uri, String content) {
        MarkLogicClient.getInstance().newXMLDocumentManager().writeAs(uri, content);
    }
}