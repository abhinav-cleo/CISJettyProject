package com.cleo.cis.server.kafka;

/**
 * Created by santoshsingh on 03/01/17.
 */
public class AnotherConsumer {
//  public static void main(String[] args) throws Exception {
//    if(args.length == 0){
//      System.out.println("Enter topic name");
//      return;
//    }
//    //Kafka consumer configuration settings
//    String topicName = args[0].toString();
//    Properties props = new Properties();
//
//    props.put("bootstrap.servers", "ec2-35-164-171-235.us-west-2.compute.amazonaws.com:9092");
//    props.put("group.id", "test");
//    props.put("enable.auto.commit", "true");
//    props.put("auto.commit.interval.ms", "1000");
//    props.put("session.timeout.ms", "30000");
//    props.put("key.deserializer",
//            "org.apache.kafka.common.serializa-tion.StringDeserializer");
//    props.put("value.deserializer",
//            "org.apache.kafka.common.serializa-tion.StringDeserializer");
//    KafkaConsumer<String, String> consumer = new KafkaConsumer
//            <String, String>(props);
//
//    //Kafka Consumer subscribes list of topics here.
//    consumer.subscribe(Arrays.asList(topicName));
//
//    //print the topic name
//    System.out.println("Subscribed to topic " + topicName);
//    int i = 0;
//
//    while (true) {
//      ConsumerRecords<String, String> records = consumer.poll(100);
//      for (ConsumerRecord<String, String> record : records)
//
//        // print the offset,key and value for the consumer records.
//        System.out.printf("offset = %d, key = %s, value = %s\n",
//                record.offset(), record.key(), record.value());
//    }
//  }
}
