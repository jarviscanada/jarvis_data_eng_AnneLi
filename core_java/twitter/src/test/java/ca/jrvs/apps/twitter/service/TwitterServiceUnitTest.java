package ca.jrvs.apps.twitter.service;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  private final String tweet_text = "Happy Easter. The service layer classes handled the business logic of the "
      + "application. When you post a tweet, the service layer is responsible to check if the tweet "
      + "text exceeds 140 characters and if long/lat is out of range. When you search for a Tweet, "
      + "you need to check if user input IDs are in the correct format.";
  Double longitude = -12.0;
  Double latitude = 12.0;
  @Test
  public void postTweet() {

    try {
      Tweet tweet = TweetObjectBuilder.tweetBuilder(tweet_text,longitude,latitude);
      dao.create(tweet);
      tweet = TweetObjectBuilder.tweetBuilder(tweet_text, longitude, latitude);
      service.postTweet(tweet);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      Tweet tweet = TweetObjectBuilder.tweetBuilder("test_tweet", -678.0, 10);
      dao.create(tweet);
      service.postTweet(tweet);
      assertFalse(false);

    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
    Tweet tweet = TweetObjectBuilder.tweetBuilder("good_tweet",longitude,latitude);
    doReturn(tweet).when(dao).create(isNotNull());
    tweet = service.postTweet(tweet);
    assertNotNull(tweet);
  }

  @Test
  public void showTweet() {
    String text = "show test tweet";
    Tweet tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
    dao.create(tweet);
    when(dao.findById(isNotNull())).thenReturn(tweet);
    String string_id = "1377475250343051266";
    Tweet returnTweet = service.showTweet(string_id, null);
    assertNotNull(returnTweet);

    try {
      service.showTweet("test123", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      service.showTweet("123.090799464686", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTweets() {
    String text = "delete test tweet";
    String string_ids[] = {"1377475250343051266"};
    Tweet tweet = TweetObjectBuilder.tweetBuilder(text,longitude,latitude);
    List<Tweet> tweets = service.deleteTweets(string_ids);
    assertNotNull(tweets);
    when(dao.deleteById(isNotNull())).thenReturn(tweet);
    try {
      service.deleteTweets(string_ids);
    } catch (IllegalArgumentException e) {
      System.out.println("one of the IDs is invalid");
    }
    try {
      service.deleteTweets(new String[]{"123456hfkjfkf", "1hdijsfgisd"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }
}