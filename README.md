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