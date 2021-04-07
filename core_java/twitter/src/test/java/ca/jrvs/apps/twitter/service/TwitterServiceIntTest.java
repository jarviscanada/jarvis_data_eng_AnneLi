package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.httphelper.HttpHelper;
import ca.jrvs.apps.twitter.dao.httphelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import com.sun.org.slf4j.internal.Logger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceIntTest {

  @InjectMocks
  TwitterDAO tDAO;
  TwitterService service;
  Tweet tweet, postedTweet;
  Double longitude;
  Double latitude;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    //set up dependency
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    tDAO = new TwitterDAO(httpHelper);
    service = new TwitterService(tDAO);
    longitude = 24d;
    latitude = -24d;
  }

  @Test
  public void postTweet() throws Exception {
    //fail, text exceed 140 chars

      String text = "test abc 123" + System.currentTimeMillis();
      tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
      postedTweet = service.postTweet(tweet);

      assertEquals(postedTweet.getText(), text);
      List<Double> coordinates = postedTweet.getCoordinates().getCoordinates();
      assertEquals(coordinates.get(0), longitude);
      assertEquals(coordinates.get(1), latitude);
  }

  @Test
  public void showTweet() {
    String text = "test abc 123" + System.currentTimeMillis();
    tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
    postedTweet = tDAO.create(tweet);
    String string_id = postedTweet.getId();
    Tweet postedTweet = service.showTweet(string_id, null);

    assertEquals(postedTweet.getText(), text);
    List<Double> coordinates = postedTweet.getCoordinates().getCoordinates();
    assertEquals(coordinates.get(0), longitude);
    assertEquals(coordinates.get(1), latitude);
  }

  @Test
  public void deleteTweets() {

    String text = "test abc 123" + System.currentTimeMillis();
    tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
    postedTweet = tDAO.create(tweet);
    List<String> string_ids = new ArrayList<String>();
    string_ids.add(postedTweet.getId());
    String text2 = "test2" + System.currentTimeMillis();
    Tweet tweet2 = TweetObjectBuilder.tweetBuilder(text2,longitude,latitude);
    Tweet postedTweet2 = tDAO.create(tweet2);
    string_ids.add(postedTweet2.getId());
    // declaration and initialise String Array
    String str_id[] = new String[string_ids.size()];
    // ArrayList to Array Conversion
    for (int i = 0; i < string_ids.size(); i++) {
      str_id[i] = string_ids.get(i);
    }
    service.deleteTweets(str_id);
    assertEquals(postedTweet.getText(), text);
    List<Double> coordinates = postedTweet.getCoordinates().getCoordinates();
    assertEquals(coordinates.get(0), longitude);
    assertEquals(coordinates.get(1), latitude);
  }
}