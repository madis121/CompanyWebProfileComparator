package ee.tlu.cwpc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table
public class Settings implements CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "web_scraper_max_pages_to_search")
	private int webScraperMaxPagesToSearch;

	@Column(name = "web_scraper_min_keyword_length")
	private int webScraperMinKeywordLength;

	@Column(name = "web_scraper_ignored_html_elements")
	private String webScraperIgnoredHTMLElements;

	@Column(name = "web_scraper_ignored_keywords")
	private String webScraperIgnoredKeywords;

	private DateTime created;

	private DateTime updated;

	public Settings() {

	}

	public Settings(int webScraperMaxPagesToSearch, int webScraperMinKeywordLength, String webScraperIgnoredHTMLElements,
			String webScraperIgnoredKeywords, DateTime created, DateTime updated) {
		this.webScraperMaxPagesToSearch = webScraperMaxPagesToSearch;
		this.webScraperMinKeywordLength = webScraperMinKeywordLength;
		this.webScraperIgnoredHTMLElements = webScraperIgnoredHTMLElements;
		this.webScraperIgnoredKeywords = webScraperIgnoredKeywords;
		this.created = created;
		this.updated = updated;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getWebScraperMaxPagesToSearch() {
		return webScraperMaxPagesToSearch;
	}

	public void setWebScraperMaxPagesToSearch(int webScraperMaxPagesToSearch) {
		this.webScraperMaxPagesToSearch = webScraperMaxPagesToSearch;
	}

	public int getWebScraperMinKeywordLength() {
		return webScraperMinKeywordLength;
	}

	public void setWebScraperMinKeywordLength(int webScraperMinKeywordLength) {
		this.webScraperMinKeywordLength = webScraperMinKeywordLength;
	}

	public String getWebScraperIgnoredHTMLElements() {
		return webScraperIgnoredHTMLElements;
	}

	public void setWebScraperIgnoredHTMLElements(String webScraperIgnoredHTMLElements) {
		this.webScraperIgnoredHTMLElements = webScraperIgnoredHTMLElements;
	}

	public String getWebScraperIgnoredKeywords() {
		return webScraperIgnoredKeywords;
	}

	public void setWebScraperIgnoredKeywords(String webScraperIgnoredKeywords) {
		this.webScraperIgnoredKeywords = webScraperIgnoredKeywords;
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
		return "Configuration [id=" + id + ", webScraperMaxPagesToSearch=" + webScraperMaxPagesToSearch
				+ ", webScraperMinKeywordLength=" + webScraperMinKeywordLength + ", webScraperIgnoredHTMLElements="
				+ webScraperIgnoredHTMLElements + ", webScraperIgnoredKeywords=" + webScraperIgnoredKeywords + ", created="
				+ created + ", updated=" + updated + "]";
	}

}
