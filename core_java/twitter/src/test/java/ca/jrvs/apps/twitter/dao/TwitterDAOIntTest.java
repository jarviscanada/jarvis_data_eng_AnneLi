package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.httphelper.HttpHelper;
import ca.jrvs.apps.twitter.dao.httphelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterDAOIntTest {
  TwitterDAO tDAO;
  Tweet tweet;
  String id = "";
  Double longitude;
  Double latitude;
  String hashTag;
  String text;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    //set up dependency
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

    //pass dependency
    tDAO = new TwitterDAO(httpHelper);

    //Set up Tweet
    hashTag = "#toronto";
    text = "hello " + hashTag + " " + "welcome to lockdown (3rd wave phase)...123";
    longitude = 12.345;
    latitude = -12.345;

    tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
  }

  @Test
  public void create() throws Exception {


    Tweet postedTweet = tDAO.create(tweet);

    assertEquals(text, postedTweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(longitude, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(latitude, tweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void findById() throws Exception {
    //Set up Tweet
    hashTag = "#toronto";
    text = "hello " + hashTag + " " + "let's find tweet by ID";
    longitude = 12.345;
    latitude = -12.345;
    tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
    Tweet postedTweet = tDAO.create(tweet);
    String tweetedStringId = postedTweet.getId();
    Tweet returnTweet = tDAO.findById(tweetedStringId);
    List<Double> coordinates = returnTweet.getCoordinates().getCoordinates();
    assertEquals(longitude, coordinates.get(0));
    assertEquals(latitude, coordinates.get(1));
    assertEquals(text, returnTweet.getText());
  }

  @Test
  public void deleteById() throws Exception {
    //Set up Tweet
    hashTag = "#toronto";
    text = "hello " + hashTag + " " + "let's delete tweet by ID";
    longitude = 12.345;
    latitude = -12.345;
    tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
    Tweet postedTweet = tDAO.create(tweet);
    String tweetedStringId = postedTweet.getId();
    Tweet deletedTweet = tDAO.deleteById(tweetedStringId);
    List<Double> coordinates = deletedTweet.getCoordinates().getCoordinates();
    assertEquals(longitude, coordinates.get(0));
    assertEquals(latitude, coordinates.get(1));
    assertEquals(text, deletedTweet.getText());
  }
}