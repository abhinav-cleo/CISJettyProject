package com.cleo.cis.server.auth.common;

import java.net.URL;
import java.io.*;
/**
 * Created by rohit on 03/01/17.
 */
public class AuthUtils {

  public static File getConfigResource() {
    String configFile = "shiros.ini";
    URL resource = AuthUtils.class.getClassLoader()
            .getResource(configFile);
    if(resource == null) {
      throw  new RuntimeException("Not able to find the shiros config file in classpath ::" + configFile);
    }
    return new File(resource.getFile());
  }
}
