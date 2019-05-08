package ee.tlu.cwpc.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.joda.time.DateTime;

@Entity
@Table
public class Profile implements CommonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Url> urls = new ArrayList<>();

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Keyword> keywords = new ArrayList<>();

  private DateTime created;

  private DateTime updated;

  public Profile() {

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

  public List<Url> getUrls() {
    return urls;
  }

  public void setUrls(List<Url> urls) {
    this.urls = urls;
  }

  public void addUrl(Url url) {
    urls.add(url);
    url.setProfile(this);
  }

  public void removeUrl(Url url) {
    urls.remove(url);
    url.setProfile(null);
  }

  public List<Keyword> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<Keyword> keywords) {
    this.keywords = keywords;
  }

  public void addKeyword(Keyword keyword) {
    keywords.add(keyword);
    keyword.setProfile(this);
  }

  public void removeKeyword(Keyword keyword) {
    keywords.remove(keyword);
    keyword.setProfile(null);
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
    return "Profile [id=" + id + ", name=" + name + ", urls=" + urls + ", keywords=" + keywords
        + ", created=" + created + ", updated=" + updated + "]";
  }

}
