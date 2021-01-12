# Quickstart

Below is a brief summary of all the steps taken in order to get up and running with this application.

## Install Apache Kafka

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
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic marklogic
```

Run (this) spring boot app
```
gradle run
```

## Testing connectivity

Send an HTTP POST (with the `Content-Type` of `application/json`) to `http://localhost:8080/api/kafka/json`
 
With some kind of JSON payload

```
{
	"hello" : "franz"
}
```


The topic listener will log:
```
c.a.kafkademo.KafkaJsonController        : I heard: {
	"hello" : "franz"
}
```

## Setting up the Kafka MarkLogic Connector

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
cp kafka-connect-marklogic-1.6.0.jar /Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0/libs/
cp config/marklogic-connect-standalone.properties /Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0/config/
cp config/marklogic-sink.properties /Users/ableasdale/Documents/workspace/kafka/kafka_2.13-2.6.0/config/
```

4. (re)start Kafka (I believe this step is necessary in order to ensure the MarkLogic connector is picked up on restart)

```
bin/kafka-server-start.sh config/server.properties
```

5. Start the MarkLogic Kafka Connector (note this is being done from the Kafka directory)

```
 bin/connect-standalone.sh config/marklogic-connect-standalone.properties config/marklogic-sink.properties
```

6. Monitor the producer `marklogic` topic

```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic marklogic
```