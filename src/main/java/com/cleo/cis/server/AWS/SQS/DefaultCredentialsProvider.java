package com.cleo.cis.server.AWS.SQS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class DefaultCredentialsProvider implements AWSCredentialsProvider {

  @Override
  public AWSCredentials getCredentials() {
      AWSCredentials cred = new AWSCredentials() {
      
      @Override
      public String getAWSSecretKey() {
        return System.getProperty("AWS_SECRET_KEY");
      }
      
      @Override
      public String getAWSAccessKeyId() {
        return System.getProperty("AWS_ACCESS_KEY_ID");
      }
    };
    return cred;
  }

  @Override
  public void refresh() {
  }
}
