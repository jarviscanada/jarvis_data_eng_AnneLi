package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.httphelper.HttpHelper;
import ca.jrvs.apps.twitter.dao.httphelper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  Tweet testTweet;
  TwitterService twitterService;
  TwitterController controller;
  Tweet tweet;
  HttpHelper httpHelper;
  TwitterDAO dao;

  @Before
  public void setUp() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    dao = new TwitterDAO(httpHelper);
    twitterService = new TwitterService(dao);
    controller = new TwitterController(twitterService);
    tweet = TweetObjectBuilder.tweetBuilder("test abc 123" + System.currentTimeMillis(), -10d, 10d);
    tweet = twitterService.postTweet(tweet);

  }

  @Test
  public void postTweet() {
    String text = "Hello another test!" + System.currentTimeMillis();
    Double latitude = 10d;
    Double longitude = -10d;
    Tweet tweet = TweetObjectBuilder.tweetBuilder(text, latitude, longitude);

    String[] args = new String[]{"post",text,longitude + ":" + latitude };
    testTweet = controller.postTweet(args);

    assertNotNull(testTweet.getCoordinates());
    assertEquals(tweet.getText(), testTweet.getText());
    assertEquals(tweet.getCoordinates().getCoordinates().get(0), testTweet.getCoordinates().getCoordinates().get(0));
    assertEquals(tweet.getCoordinates().getCoordinates().get(1), testTweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void showTweet() {
    String[] args = new String[]{"show",tweet.getIdStr()};
    testTweet = controller.showTweet(args);

    assertEquals(tweet.getIdStr(), testTweet.getIdStr());
    assertEquals(tweet.getText(), testTweet.getText());
  }

  @Test
  public void deleteTweet() {
    String[] args = new String[]{"delete", tweet.getIdStr()};
    List<Tweet> deletedTweets = controller.deleteTweet(args);
    assertNotNull(deletedTweets);
    assertEquals(tweet.getId(), deletedTweets.get(0).getId());
  }
}
