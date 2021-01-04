## Quickstart
Install Apache Kafka

https://kafka.apache.org/downloads

Start Zookeeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka

```
bin/kafka-server-start.sh config/server.properties
```

Monitor the test topic

```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic FirstTopic
```

Run the spring boot app
```
gradle run
```

send an HTTP POST to http://localhost:8080/api/kafka
 
With the payload

```
{
	"firstname" : "alex",
	"surname" : "bleasdale"
}
```


The topic listener will log

```
2020-12-19 09:50:29.664  INFO 23000 --- [ntainer#0-0-C-1] c.a.kafkademo.KafkaController            : I heard: Model{firstname='alex', surname='bleasdale'}
```

Setting up the Kafka MarkLogic Connector

1. Download (clone) the Kafka MarkLogic Connector

```
git clone git@github.com:marklogic-community/kafka-marklogic-connector.git
```
2. Configure the Kafka MarkLogic Connector
```
cd kafka-marklogic-connector/
cp gradle.properties gradle-local.properties
vim gradle-local.properties
# Set the kafakHome variable to point at your Kafka install - e.g. kafkaHome=/Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0
./gradlew clean jar
```
3. Copy over the necessary files

```
cp kafka-connect-marklogic-1.5.0.jar /Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0/libs/
cp config/marklogic-connect-standalone.properties /Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0/config/
cp config/marklogic-sink.properties /Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0/config/
```

4. (re)start Kafka(I think this is needed to ensure the MarkLogic connector is picked up?)

```
bin/kafka-server-start.sh config/server.properties
```

5. Start the MarkLogic Kafka Connector

```
 bin/connect-standalone.sh config/marklogic-connect-standalone.properties config/marklogic-sink.properties
```

6. Monitor the producer `marklogic` topic

```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic marklogic
```