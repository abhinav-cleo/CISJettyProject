package com.cleo.cis.server.AWS.SQS;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.cleo.cis.server.AWS.AWSUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rohit on 05/01/17.
 */
public class AWSSQSClient {

  public static ArrayList<String> receiveMessage() {
    AmazonSQSClient client = getAmazonSQSClient();
    ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(
            "https://sqs.us-west-2.amazonaws.com/208021272981/TestStandardQueue");
    List<Message> messages = client.receiveMessage(receiveMessageRequest).getMessages();
    ArrayList<String> msgList = new ArrayList<>();
    for (Message message : messages) {
        msgList.add(message.getBody());
    }
    return msgList;
  }

  public static void sendMessage(String message) {
    AmazonSQSClient client = getAmazonSQSClient();
    System.out.println("sending the message");
    client.sendMessage(new SendMessageRequest("https://sqs.us-west-2.amazonaws.com/208021272981/TestStandardQueue",
        message));
  }

  private static AmazonSQSClient getAmazonSQSClient() {
    return new AmazonSQSClient(AWSUtils.getCredentials());
  }

  public static void main(String[] args) throws Exception {
//    sendMessage("testing message send.");
      sendUsingRest();
  }

  private static void sendUsingRest() throws IOException {
    URL url = new URL("http://localhost:8680/rest/awsService/sendMessage");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);

    // Send GCM message content.
    OutputStream outputStream = conn.getOutputStream();

    outputStream.write("test message from rohit".getBytes());
    InputStream inputStream = conn.getInputStream();
    String daat = IOUtils.toString(inputStream);
    System.out.println(daat);
  }
}
