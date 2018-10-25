package ee.tlu.cwpc.dto;

import java.util.List;

public class CollectedData {

	private List<String> websites;

	private List<WebsiteKeyword> keywords;

	public CollectedData(List<String> websites, List<WebsiteKeyword> keywords) {
		this.websites = websites;
		if (keywords.size() > 50) {
			this.keywords = keywords.subList(0, 50);
		} else {
			this.keywords = keywords;
		}
	}

	public List<String> getWebsites() {
		return websites;
	}

	public List<WebsiteKeyword> getKeywords() {
		return keywords;
	}

}
