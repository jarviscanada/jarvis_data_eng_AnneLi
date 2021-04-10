package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TweetObjectBuilder {

  /**
   * Takes a body of text, longitude, latitude
   * and creates a Tweet object
   *
   * @param text
   * @param longitude
   * @param latitude
   *
   */

  public static Tweet tweetBuilder(String text, double longitude, double latitude) {
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    List<Double> geoObjects = new ArrayList<>();
    geoObjects.add(longitude);
    geoObjects.add(latitude);
    coordinates.setCoordinates(geoObjects);
    tweet.setCoordinates(coordinates);
    tweet.setText(text);

    return tweet;
  }
}
