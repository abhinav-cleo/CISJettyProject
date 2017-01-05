package com.cleo.cis.server.kafka.sample.consumer;

import com.cleo.cis.server.mongo.sample.EventRepo;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by santoshsingh on 04/01/17.
 */
public class ConsumerGroupExample {
  private final ConsumerConnector consumer;
  private final String topic;
  private  ExecutorService executor;

  public ConsumerGroupExample(String a_zookeeper, String a_groupId, String a_topic) {
    consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
            createConsumerConfig(a_zookeeper, a_groupId));
    this.topic = a_topic;
  }

  public void shutdown() {
    if (consumer != null) consumer.shutdown();
    if (executor != null) executor.shutdown();
    try {
      if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
        System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted during shutdown, exiting uncleanly");
    }
  }

  public void run(int a_numThreads, EventRepo eventRepo) {
    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(topic, new Integer(a_numThreads));
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

    // now launch all the threads
    //
    executor = Executors.newFixedThreadPool(a_numThreads);

    // now create an object to consume the messages
    //
    int threadNumber = 0;
    for (final KafkaStream stream : streams) {
      executor.submit(new ConsumerTest(stream, threadNumber, eventRepo));
      threadNumber++;
    }
  }

  private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
    Properties props = new Properties();
    props.put("zookeeper.connect", a_zookeeper);
    props.put("group.id", a_groupId);
    props.put("zookeeper.session.timeout.ms", "1000");
    props.put("zookeeper.sync.time.ms", "1000");
    props.put("auto.commit.interval.ms", "1000");
    props.put("auto.offset.reset", "smallest");

    return new ConsumerConfig(props);
  }

  public static void main(String[] args) throws UnknownHostException {
    String zooKeeper = "ec2-35-164-171-235.us-west-2.compute.amazonaws.com:2181";
    String groupId = "test";
    String topic = "test";
    int threads = 1;

    EventRepo eventRepo = new EventRepo("ec2-35-166-252-121.us-west-2.compute.amazonaws.com", 27017, "siteA", "test");
    ConsumerGroupExample example = new ConsumerGroupExample(zooKeeper, groupId, topic);
    example.run(threads, eventRepo);

    try {
      Thread.sleep(10000);
    } catch (InterruptedException ie) {

    }
    example.shutdown();
  }
}
