package ee.tlu.cwpc.dto;

import java.util.List;

public class WebsiteData {

	private String url;

	private List<WebsiteKeyword> keywords;

	public WebsiteData() {

	}

	public WebsiteData(String url, List<WebsiteKeyword> keywords) {
		this.url = url;
		this.keywords = keywords;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<WebsiteKeyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<WebsiteKeyword> keywords) {
		this.keywords = keywords;
	}

}
