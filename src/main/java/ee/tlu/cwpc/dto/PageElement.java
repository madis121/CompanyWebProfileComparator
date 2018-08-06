package ee.tlu.cwpc.dto;

public class PageElement {

	private String tag;

	private String textContent;

	public PageElement(String tag, String textContent) {
		this.tag = tag;
		this.textContent = textContent;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@Override
	public String toString() {
		return "PageElement [tag=" + tag + ", textContent=" + textContent + "]";
	}

}