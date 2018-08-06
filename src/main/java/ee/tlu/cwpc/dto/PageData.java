package ee.tlu.cwpc.dto;

import java.util.Arrays;
import java.util.List;

public class PageData {

	private String url;

	private List<PageElement> pageElements;

	public PageData() {

	}

	public PageData(String url, List<PageElement> pageElements) {
		this.url = url;
		this.pageElements = pageElements;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<PageElement> getPageElements() {
		return pageElements;
	}

	public void setPageElements(List<PageElement> pageElements) {
		this.pageElements = pageElements;
	}

	@Override
	public String toString() {
		return "PageData [url=" + url + ", pageElements=" + Arrays.toString(pageElements.toArray()) + "]";
	}

}
