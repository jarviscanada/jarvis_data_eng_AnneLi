package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;

  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }
  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    if (!(validateID(id))) {
      throw new IllegalArgumentException("Error: Invalid Id!");
    } else {
      return (Tweet) dao.findById(id);
    }
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> tweets = new ArrayList<Tweet>();
    for (String id : ids) {
      if (!validateID(id)) {
        throw new IllegalArgumentException("The ID is invalid!");
      }
      tweets.add((Tweet) dao.deleteById(id));
    }
    return tweets;
  }

  private void validatePostTweet(Tweet tweet) {
    Double longitude = tweet.getCoordinates().getCoordinates().get(0);
    Double latitude = tweet.getCoordinates().getCoordinates().get(1);
    String text = tweet.getText();
    //validate text length, lat/long
    if (text.length() > 140) {
      throw new IllegalArgumentException("Error: Tweet cannot exceed 140 characters");
    } if(longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Error: The longitude of the location is only valid "
          + "between -180.0 to +180.0");
    } if (latitude < -90 || longitude > 90) {
      throw new IllegalArgumentException("The latitude of the location is only valid between inside "
          + "the range -90.0 to +90.0");
    }
  }

  private boolean validateID(String id) {
    //make sure string is a number
    if (id == null) {
      throw new IllegalArgumentException("Error: Tweet id cannot be null!");
    } if (id.matches("\\d*")) {
      return true;
    }
    return false;
  }
}
