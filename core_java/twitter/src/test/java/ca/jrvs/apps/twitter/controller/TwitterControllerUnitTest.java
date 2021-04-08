package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  Service service;

  @InjectMocks
  TwitterController controller;

  Tweet tweet;
  String args[];

  @Before
  public void setUp() {
    controller = new TwitterController(service);
  }
  @Test
  public void postTweet() {
    //unhappy path
    try {
      args = new String[]{"post", "1", "13", "12", "21"};
      controller.postTweet(args);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
    //invalid coordinates
    try {
      args = new String[]{"post", "tweet_text", "a:a"};
      controller.postTweet(args);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }

    when(service.postTweet(any())).thenReturn(TweetObjectBuilder.tweetBuilder("this_is_a_test_tweet", 10.0, -10.0));
    String[] args = new String[]{"post", "this_is_a_test_tweet", "10d:-10d"};
    Tweet test_tweet = controller.postTweet(args);
    assertNotNull(test_tweet);
    assertNotNull(test_tweet.getText());
  }

  @Test
  public void showTweet() {
    tweet = TweetObjectBuilder.tweetBuilder("test_tweet", -10.0, 10.0);
    when(service.showTweet(any(), any())).thenReturn(this.tweet);
    //unhappy path
    try {
      args = new String[]{"show", "123abc", "9090909090", "hghgyryrty"};
      controller.showTweet(args);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
    //happy path
    String[] args = new String[]{"show", "122345622432566"};

    tweet = controller.showTweet(args);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
  }

  @Test
  public void deleteTweet() {
    when(service.deleteTweets(any())).thenReturn(Arrays.asList(tweet));
    try {
      args = new String[] {"delete"};
      controller.deleteTweet(args);
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
    List<Tweet> tweets = new ArrayList<>();
    tweet = TweetObjectBuilder.tweetBuilder("this_is_a_test_tweet" + System.currentTimeMillis(), -10.0, 10.0);
    tweets.add(tweet);
    args = new String[] {"delete", "12345"};
    tweets = controller.deleteTweet(args);
    assertNotNull(tweets);
  }
}