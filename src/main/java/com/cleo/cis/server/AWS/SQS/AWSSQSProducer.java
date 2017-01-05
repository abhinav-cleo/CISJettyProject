package com.cleo.cis.server.AWS.SQS;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class AWSSQSProducer {
  
  public void sendMessage(String msg) throws JMSException {

    //Create the connection to the AWS
    SQSConnectionFactory connectionFactory = SQSConnectionFactory.builder()
        .withRegion(Region.getRegion(Regions.US_WEST_2))
        .withAWSCredentialsProvider(new DefaultCredentialsProvider())
        .build();

    System.out.println("Created connectionFactory");
    
    // Create the connection.
    SQSConnection connection = connectionFactory.createConnection();
    AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

    // Create an SQS queue named 'TestQueue' – if it does not already exist.
    if (!client.queueExists("MyTestQueue")) {
      client.createQueue("MyTestQueue");
    }

    //Create session
    Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
    Queue queue = session.createQueue("MyTestQueue");

    MessageProducer producer = session.createProducer(queue);
    TextMessage message = session.createTextMessage(msg);

    // Send the message.
    producer.send(message);
    connection.close();
  }
  
  public static void main(String args[]) throws JMSException {
    AWSSQSProducer producer = new AWSSQSProducer();
    producer.sendMessage("Test Mohan12345");
  }
}
