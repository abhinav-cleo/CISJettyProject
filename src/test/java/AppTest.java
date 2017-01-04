import com.cleo.cis.server.auth.common.AuthException;
import com.cleo.cis.server.auth.common.AuthUtils;
import com.cleo.cis.server.auth.shiros.ShirosProvider;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by rohit on 03/01/17.
 */
public class AppTest {

  @Test
  public void test() {
    System.out.println("\n\n\n\n ------- Just a test ---------- \n\n\n\n");
  }

  @Test
  public void testResource() throws IOException {
    File file = new File("test.ini");
    if(file.exists()) {
      List<String> strings = FileUtils.readLines(file);
      Assert.assertTrue(strings.size() > 0);
      return;
    }
    File configResource = AuthUtils.getConfigResource();
    FileInputStream in = new FileInputStream(configResource);
    List<String> strings = IOUtils.readLines(in);
    Assert.assertTrue(strings.size() > 0);
  }



  @Test
  public void testShiros() throws AuthException {
    ShirosProvider.isValidUser("testing");
  }
}
