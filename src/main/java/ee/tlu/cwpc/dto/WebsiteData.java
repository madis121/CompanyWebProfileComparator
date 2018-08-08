package ee.tlu.cwpc.dto;

import java.util.ArrayList;
import java.util.List;

public class WebsiteData {

	private String url;

	private List<String> words = new ArrayList<>();

	public WebsiteData() {

	}

	public WebsiteData(String url, List<String> words) {
		this.url = url;
		this.words = words;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

}
