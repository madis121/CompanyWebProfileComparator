package ee.tlu.cwpc.dto;

import java.util.HashMap;
import java.util.Map;

public class WebsiteData {

	private String url;

	private Map<String, Integer> words = new HashMap<>();

	public WebsiteData() {

	}

	public WebsiteData(String url, Map<String, Integer> words) {
		this.url = url;
		this.words = words;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Integer> getWords() {
		return words;
	}

	public void setWords(Map<String, Integer> words) {
		this.words = words;
	}

}
