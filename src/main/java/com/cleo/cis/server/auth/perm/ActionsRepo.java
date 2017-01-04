package com.cleo.cis.server.auth.perm;

import com.cleo.cis.server.auth.common.AuthException;
import org.json.JSONArray;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rohit on 04/01/17.
 */
public class ActionsRepo {

  private static Set<String> actionsSet = Collections.synchronizedSet(new HashSet<>());
  public static void addAction(String assetName) throws AuthException {
    if(actionsSet.contains(assetName)) {

      throw new AuthException("Asset with asset name already present " + assetName);
    }
    actionsSet.add(assetName);
  }

  public static boolean removeAction(String assetName) {
    return actionsSet.remove(assetName);
  }

  public static boolean actionExists(String actionName) {
    return actionsSet.contains(actionName);
  }

  public static JSONArray getAllActions() {
    return new JSONArray(actionsSet);
  }

}
