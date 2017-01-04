package com.cleo.cis.server.auth.perm;

import com.cleo.cis.server.auth.common.AuthException;
import org.json.JSONArray;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rohit on 04/01/17.
 */
public class AssetRepo {

  private static Set<String> assetSet = Collections.synchronizedSet(new HashSet<>());

  public static void addAsset(String assetName) throws AuthException {
    if(assetSet.contains(assetName)) {
      throw new AuthException("Asset with asset name already present " + assetName);
    }
    assetSet.add(assetName);
  }

  public static boolean assetExists(String assetName) {
    return assetSet.contains(assetName);
  }

  public static boolean removeAsset(String assetName) {
    return assetSet.remove(assetName);
  }

  public static JSONArray getAllAssets() {
    return new JSONArray(assetSet);
  }
}
