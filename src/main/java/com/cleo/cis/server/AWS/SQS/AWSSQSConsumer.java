package com.cleo.cis.server.AWS.SQS;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class AWSSQSConsumer {
  
  public static boolean started = false;

  SQSConnection connection = null;
  Session session = null;
 
  
  public static void start() throws JMSException, InterruptedException {
    if(started)return;
    started = true;
    SQSConnectionFactory connectionFactory = SQSConnectionFactory.builder()
        .withRegion(Region.getRegion(Regions.US_WEST_2))
        .withAWSCredentialsProvider(new  DefaultCredentialsProvider())
        .build();

    System.out.println(" Created ConnectionFactory");
    // Create the connection.
    SQSConnection connection = connectionFactory.createConnection();
    AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

    Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
    Queue queue = session.createQueue("MyTestQueue");

    MessageConsumer consumer = session.createConsumer(queue);

    // Instantiate and set the message listener for the consumer.
    consumer.setMessageListener(new AWSMessageListener());

    // Start receiving incoming messages.
    connection.start();
    Thread.sleep(1000);
    System.out.println("Startinng Listening for messaegs...");
  }
  
  public void stop() throws JMSException {
    if (session != null) {
      session.close();
    }
    if (connection != null) {
      connection.close();
    }
  }

  public static void main(String[] args) throws JMSException, InterruptedException {
    System.out.println(System.getProperty("AWS_ACCESS_KEY_ID"));
    AWSSQSConsumer awssqsConsumer = new AWSSQSConsumer();
    awssqsConsumer.start();
    Thread.sleep(60000);
    System.out.println(" Stopping receive of messages....");
    awssqsConsumer.stop();
  }
}
