package com.cleo.cis.server.AWS.SQS;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class AWSMessageListener implements MessageListener {
  @Override
  public void onMessage(Message message) {
    System.out.println(message);
    try {
      // Cast the received message as TextMessage and print the text to screen.
      if (message != null) {
        System.out.println("Received: " + ((TextMessage) message).getText());
      }
      message.acknowledge();
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
