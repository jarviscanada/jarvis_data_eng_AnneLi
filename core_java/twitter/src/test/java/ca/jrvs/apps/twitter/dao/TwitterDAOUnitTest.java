package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.httphelper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDAOUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDAO dao;

  private String tweet = "{\n"
      + "\"created_at\":\"Tue Mar 29 20:56:14 +0000 2021\",\n"
      + " \"id\":1356708027643092996,\n"
      + " \"id_str\":\"1356708027643092996\",\n"
      + " \"text\":\"Happy EAster\",\n"
      + " \"entities\":{\n"
      + "     \"hashtags\":[],"
      + "     \"user_mentions\":[]"
      + " },\n"
      + " \"coordinates\":{\n"
      + "     \"coordinates\":[-12, 12],\n \"type\":\"Point\"},\n"
      + " \"retweet_count\":0,\n"
      + " \"favorite_count\":0,\n "
      + "\"favorited\":false,\n"
      + " \"retweeted\":false\n"
      + "}";

  @Test
  public void create() throws Exception {
    String text = "Happy Easter";
    Double longitude = 12.345;
    Double latitude = -12.345;

    // Test failed httpPost
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("httpPost failed"));
    try {
      dao.create(TweetObjectBuilder.tweetBuilder(text, longitude, latitude));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // successful post request
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDAO spyDAO = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(this.tweet, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDAO).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDAO.create(TweetObjectBuilder.tweetBuilder(text, longitude, latitude));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void findById() throws Exception {
    String id = "1377475250343051266";

    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("httpGet failed"));

    try {
      dao.findById(id);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDAO spyDAO = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(this.tweet, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDAO).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDAO.findById(id);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteById() throws Exception {
    String id = "1377475250343051266";

    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("httpGet failed"));

    try {
      dao.findById(id);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    //when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDAO spyDAO = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(this.tweet, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDAO).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDAO.deleteById(id);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }
}
