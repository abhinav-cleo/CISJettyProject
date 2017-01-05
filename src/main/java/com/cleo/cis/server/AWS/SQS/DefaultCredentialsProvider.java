package com.cleo.cis.server.AWS.SQS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class DefaultCredentialsProvider implements AWSCredentialsProvider {

  @Override
  public AWSCredentials getCredentials() {
      AWSCredentials cred = new AWSCredentials() {
      
      @Override
      public String getAWSSecretKey() {
        return "9Jd1dG/7QvMuK3YbIDOiHvz8Ubh2+AMWmaghfjqZ";
      }
      
      @Override
      public String getAWSAccessKeyId() {
        return "AKIAJF67V6PNE7IUXF3A";
      }
    };
    return cred;
  }

  @Override
  public void refresh() {
  }
}
