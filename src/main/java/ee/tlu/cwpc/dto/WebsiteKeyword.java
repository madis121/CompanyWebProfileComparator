package ee.tlu.cwpc.dto;

public class WebsiteKeyword implements Comparable<WebsiteKeyword> {

  private String value;

  private int count;

  public WebsiteKeyword() {

  }

  public WebsiteKeyword(String value, int count) {
    this.value = value;
    this.count = count;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "WebsiteKeyword [value=" + value + ", count=" + count + "]";
  }

  @Override
  public int compareTo(WebsiteKeyword comparable) {
    return comparable.getCount() - this.count;
  }

}
