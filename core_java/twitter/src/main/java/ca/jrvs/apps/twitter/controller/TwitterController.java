package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.StringUtil;
import ca.jrvs.apps.twitter.util.TweetObjectBuilder;
import java.util.List;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args, tweet_str
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude: longitude\"");
    }

    String tweet_text = args[1];
    String coordinates = args[2];
    String[] coordinateArray = coordinates.split(COORD_SEP);
    if (coordinateArray.length != 2 || StringUtil.isEmpty(tweet_text)) {
      throw new IllegalArgumentException(
          "Invalid local information format\nUSAGE: TwitterCLIApp post \"tweet_text\"\"latitude:longitude\""
      );
    }
    Double longitude = null;
    Double latitude = null;
    try {
      latitude = Double.parseDouble(coordinateArray[0]);
      longitude = Double.parseDouble(coordinateArray[1]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Invalid location information format\nUSAGE: TwitterCLIApp post \"tweet_text\"\"latitude:longitude\""
      );
    }
    Tweet posted_tweet = TweetObjectBuilder.tweetBuilder(tweet_text, longitude, latitude);
    return service.postTweet(posted_tweet);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet showTweet(String[] args) {
    String id;
    String[] fields;
    if (args.length == 2) {
      id = args[1];
      return service.showTweet(id, null);
    } else if (args.length == 3) {
      id = args[1];
      fields = args[2].split(COMMA);
      return  service.showTweet(id, fields);
    } else {
      throw new IllegalArgumentException(
        "USAGE: TwitterCLIApp show tweet_id [field1, field2...]"
      );
    }
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    String[] ids;
    if (args.length < 2) {
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp delete [tweet_id1, tweet_id2...]"
      );
    } else {
      ids = args[1].split(COMMA);
      return service.deleteTweets(ids);
    }
  }
}
