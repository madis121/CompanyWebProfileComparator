package ee.tlu.cwpc.dto;

import java.util.List;

public class CollectedData {

	private List<String> websites;

	private List<WebsiteKeyword> keywords;

	public CollectedData(List<String> websites, List<WebsiteKeyword> keywords) {
		this.websites = websites;
		this.keywords = keywords;
	}

	public List<String> getWebsites() {
		return websites;
	}

	public List<WebsiteKeyword> getKeywords() {
		return keywords;
	}

}
