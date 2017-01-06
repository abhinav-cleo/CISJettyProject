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
          String aws_secret_key = System.getProperty("AWS_SECRET_KEY");
          System.out.println(aws_secret_key);
          return aws_secret_key;
        }

        @Override
        public String getAWSAccessKeyId() {
          return System.getProperty("AWS_ACCESS_KEY_ID");
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
