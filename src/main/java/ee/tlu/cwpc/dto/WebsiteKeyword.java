package ee.tlu.cwpc.dto;

public class WebsiteKeyword implements Comparable<WebsiteKeyword> {

	private String word;

	private int count;

	public WebsiteKeyword() {

	}

	public WebsiteKeyword(String word, int count) {
		this.word = word;
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "WebsiteKeyword [word=" + word + ", count=" + count +"]";
	}

	@Override
	public int compareTo(WebsiteKeyword comparable) {
		return comparable.getCount() - this.count;
	}

}
