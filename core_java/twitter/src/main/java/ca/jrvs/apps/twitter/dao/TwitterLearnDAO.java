package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;

public class TwitterLearnDAO implements CrdDao<Tweet, String> {

  @Override
  public Tweet create (Tweet tweet) {
    //parse ourTweetObject to obtain tweet msg to be posted on Twitter
    //determine URI for making posts on Twitter/ determine http method suitable for creating the tweet e.g get/post/put
      //https://api.twitter.com/1.1/statuses/update.json?status=This is a another test tweet
      // add parameters by concatenation. example if tweet.getCordinates().getLatitude and
      // ../.getLongitude are not empty string
      // &lat=tweet.getCordinates().getLatitude()&long=tweet.getCordinates().getLongitude()
    //make the post on twitter by invoking suitable http request
    //parse twitter's response
    //populate out TweetObj
    //return our tweet obj
    return null;
  }

  @Override
  public Tweet findById(String s) {
    return null;
  }

  @Override
  public Tweet deleteById(String s) {
    return null;
  }
}
