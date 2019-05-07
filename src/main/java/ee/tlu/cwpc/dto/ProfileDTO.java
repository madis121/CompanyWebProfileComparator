package ee.tlu.cwpc.dto;

import org.joda.time.DateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ee.tlu.cwpc.serializer.CustomDateTimeSerializer;

public class ProfileDTO {

  private long id;

  private String name;

  private String keywords;

  @JsonSerialize(using = CustomDateTimeSerializer.class)
  private DateTime created;

  @JsonSerialize(using = CustomDateTimeSerializer.class)
  private DateTime updated;

  public ProfileDTO(long id, String name, DateTime created, DateTime updated) {
    this.id = id;
    this.name = name;
    this.created = created;
    this.updated = updated;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public DateTime getCreated() {
    return created;
  }

  public void setCreated(DateTime created) {
    this.created = created;
  }

  public DateTime getUpdated() {
    return updated;
  }

  public void setUpdated(DateTime updated) {
    this.updated = updated;
  }

  @Override
  public String toString() {
    return "ProfileDTO [id=" + id + ", name=" + name + ", keywords=" + keywords + ", created="
        + created + ", updated=" + updated + "]";
  }

}
