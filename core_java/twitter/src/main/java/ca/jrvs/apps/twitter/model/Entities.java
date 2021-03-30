//-----------------------------------ca.jrvs.apps.twitter.model.Entities.java-----------------------------------

package ca.jrvs.apps.twitter.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hashtags",
    "urls",
    "user_mentions"
})
public class Entities {

  @JsonProperty("hashtags")
  private String hashtags;
  @JsonProperty("urls")
  private String urls;
  @JsonProperty("user_mentions")
  private String userMentions;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("hashtags")
  public String getHashtags() {
    return hashtags;
  }

  @JsonProperty("hashtags")
  public void setHashtags(String hashtags) {
    this.hashtags = hashtags;
  }

  @JsonProperty("urls")
  public String getUrls() {
    return urls;
  }

  @JsonProperty("urls")
  public void setUrls(String urls) {
    this.urls = urls;
  }

  @JsonProperty("user_mentions")
  public String getUserMentions() {
    return userMentions;
  }

  @JsonProperty("user_mentions")
  public void setUserMentions(String userMentions) {
    this.userMentions = userMentions;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
