package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.httphelper.HttpHelper;
import ca.jrvs.apps.twitter.dao.httphelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import org.junit.Before;
import org.junit.Test;

public class TwitterDAOIntTest {
  private TwitterDAO tDAO;
  private Tweet testTweet;
  private String id;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    //set up dependency
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
    //pass dependency
    TwitterDAO tDAO = new TwitterDAO(httpHelper);
    String text = "The time is " + System.currentTimeMillis();
    double longitude = 12.345, latitude = -12.345;
    Tweet tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
  }

  @Test
  public void create() throws Exception {
    String hashTag = "#toronto";
    String text = "hello " + hashTag + " " + "welcome to lockdown (3rd wave phase)";
    Double latitude = 1d;
    Double longitude = -1d;
    Tweet tweet = TweetObjectBuilder.tweetBuilder(text, longitude, latitude);
    Tweet postedTweet = tDAO.create(tweet);

    assertEquals(text, postedTweet.getText());

    assertEquals(text, postedTweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(longitude, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(latitude, tweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void findById() throws Exception {
    Tweet returnTweet = tDAO.findById(id);
  }

  @Test
  public void deleteById() throws Exception {
  }
}