package com.cleo.cis.server.AWS.SQS;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.cleo.cis.server.AWS.DB.AWSDynamoClient;

public class AWSMessageListener implements MessageListener {
  @Override
  public void onMessage(Message message) {
    System.out.println(message);
    try {
      // Cast the received message as TextMessage and print the text to screen.
      if (message != null) {
        AWSDynamoClient.addEventToTable(((TextMessage) message).getText());

        System.out.println("Received: " + ((TextMessage) message).getText());
      }
      
      
      message.acknowledge();
    } catch (JMSException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}
