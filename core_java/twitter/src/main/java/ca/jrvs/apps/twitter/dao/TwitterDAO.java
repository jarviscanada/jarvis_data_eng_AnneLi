package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.httphelper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterDAO implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";
  //URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  private static final Logger logger = LoggerFactory.getLogger(TwitterDAO.class);

  /**
   * Constructor generates a TwitterDAO object with
   * the requirement of passing in a httpHelper object parameter,
   * which is a TwitterHttpHelper object
   *
   */
  private HttpHelper httpHelper;

  public TwitterDAO(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   *
   * @param tweet
   * @return tweet
   */
  @Override
  public Tweet create(Tweet tweet) {
    //Construct URI
    URI uri;
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String text = percentEscaper.escape(tweet.getText());
    String longitude = String.valueOf(tweet.getCoordinates().getCoordinates().get(0));
    String latitude = String.valueOf(tweet.getCoordinates().getCoordinates().get(1));
    try {
      String s = API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + text +
          AMPERSAND + "long" + EQUAL + longitude + AMPERSAND + "lat" + EQUAL + latitude;
      logger.debug(s);
      // parses the string into a URI
      uri = new URI(s);
      //Execute HTTP Request
      HttpResponse response = httpHelper.httpPost(uri);

      //Validate response amd extract response to Tweet object
      return parseResponseBody(response, HTTP_OK);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Missing required parameter: status. Error executing HTTP request", e);
    }
  }

  @Override
  public Tweet findById(String id) {
    URI uri;
    try {
      uri = URI.create(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id);
      //Execute HTTP Request
      HttpResponse response = httpHelper.httpGet(uri);
      return parseResponseBody(response, HTTP_OK);
    } catch (NullPointerException | IllegalArgumentException e) {
      throw new IllegalArgumentException("Missing required parameter: id. Error executing HTTP request", e);
    }
  }

  @Override
  public Tweet deleteById(String id) {
    URI uri;
    try {
      uri = URI.create(API_BASE_URI + DELETE_PATH + "/" + id + ".json");
      HttpResponse response = httpHelper.httpPost(uri);
      return parseResponseBody(response, HTTP_OK);
    } catch (NullPointerException | IllegalArgumentException e) {
      throw new IllegalArgumentException("Missing required parameter: id. Error executing HTTP request", e);
    }
  }

  /**
   * Check response status code and convert Response Entity to Tweet
   */
  public Tweet parseResponseBody (HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;

    //Check response status
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status:" + status);
    }

    if (response.getEntity() == null) {
      throw new RuntimeException("Empty response body");
    }

    //Convert Response Entity to Str
    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to String", e);
    }

    //Deserialize JSON string to Tweet object
    try {
      tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert from JSON str to Object", e);
    }
    return tweet;
  }

}
