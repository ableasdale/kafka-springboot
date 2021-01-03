package com.alexbleasdale.kafkademo;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

public class MarkLogicClient {

    //private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /* Initialization on Demand holder idiom: - create the client only once (on first use) */
    private static class LazyHolder {
        static final DatabaseClient INSTANCE = DatabaseClientFactory
                .newClient("localhost", 8000, "Documents",
                        new DatabaseClientFactory.DigestAuthContext("admin", "admin"));
    }

    public static DatabaseClient getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void writeXmlDocument(String uri, String content) {
        MarkLogicClient.getInstance().newXMLDocumentManager().writeAs(uri, content);
    }
}