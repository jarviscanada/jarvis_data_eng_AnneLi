package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Tweet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtil {

  /**
   * JsonParser:
   * Parses a JSON string from an HTTP response from Twitter REST API and maps the fields to the
   * getters defined in class Tweet, generating a Tweet object.
   * @param json
   * @param cls
   */
  public static Tweet toObjectFromJson(String json, Class cls) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return (Tweet) objectMapper.readValue(json, cls);
  }

  /**
   * Enable pretty print JSON output using Jackson
   */
  public static String toPrettyJSON(Tweet tweet) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tweet);
    return json;
  }
}
