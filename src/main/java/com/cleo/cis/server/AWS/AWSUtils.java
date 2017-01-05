package com.cleo.cis.server.AWS;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;

/**
 * Created by rohit on 05/01/17.
 */
public class AWSUtils {

  public static AWSCredentials getCredentials() {
    AWSCredentials credentials = null;
    try {
      credentials = new AWSCredentials() {

        @Override
        public String getAWSSecretKey() {
          // TODO Auto-generated method stub
          return "9Jd1dG/7QvMuK3YbIDOiHvz8Ubh2+AMWmaghfjqZ";
        }

        @Override
        public String getAWSAccessKeyId() {
          // TODO Auto-generated method stub
          return "AKIAJF67V6PNE7IUXF3A";
        }
      };
    } catch (Exception e) {
      throw new AmazonClientException(
              "Cannot load the credentials from the credential profiles file. " +
                      "Please make sure that your credentials file is at the correct " +
                      "location (~/.aws/credentials), and is in valid format.",
              e);
    }
    return credentials;
  }
}
