package com.cleo.cis.server.AWS.SQS;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.cleo.cis.server.AWS.AWSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rohit on 05/01/17.
 */
public class AWSSQSClient {

  public static void main(String[] args) {
    sendMessage("testing");
  }


  public static ArrayList<String> receiveMessage() {
    AmazonSQSClient client = getAmazonSQSClient();
    ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest("https://sqs.us-west-2.amazonaws.com/208021272981/TestStandardQueue");
    List<Message> messages = client.receiveMessage(receiveMessageRequest).getMessages();
    ArrayList<String> msgList = new ArrayList<>();
    for (Message message : messages) {
        msgList.add(message.getBody());
    }
    return msgList;
//    for (Message message : messages) {
//      System.out.println("  Message");
//      System.out.println("    MessageId:     " + message.getMessageId());
//      System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
//      System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
//      System.out.println("    Body:          " + message.getBody());
//
//      for (Map.Entry<String, String> entry : message.getAttributes().entrySet()) {
//        System.out.println("  Attribute");
//        System.out.println("    Name:  " + entry.getKey());
//        System.out.println("    Value: " + entry.getValue());
//      }
//    }
  }

  public static void sendMessage(String message) {
    AmazonSQSClient client = getAmazonSQSClient();
    client.sendMessage(new SendMessageRequest("https://sqs.us-west-2.amazonaws.com/208021272981/TestStandardQueue",
        message));
  }

  private static AmazonSQSClient getAmazonSQSClient() {
    return new AmazonSQSClient(AWSUtils.getCredentials());
  }

}
