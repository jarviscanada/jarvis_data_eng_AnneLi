package ca.jrvs.apps.twitter.dao.httphelper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperUnitTest {

  String consumerKey;
  String consumerSecret;
  String accessToken;
  String tokenSecret;
  HttpHelper httpHelper;

  @Before
  public void setUp() {
    consumerKey = System.getenv("consumerKey");
    consumerSecret = System.getenv("consumerSecret");
    accessToken = System.getenv("accessToken");
    tokenSecret = System.getenv("tokenSecret");
    httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
  }

  @Test
  public void httpPost() throws Exception {

    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    //Create components

    HttpResponse response = httpHelper
        .httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=testtest"));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  @Test
  public void httpGet() throws IOException, URISyntaxException {
    HttpResponse httpResponse = httpHelper
        .httpGet(new URI("https://api.twitter.com/1.1/statuses/show.json?id=1377475250343051266"));
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
  }
}
