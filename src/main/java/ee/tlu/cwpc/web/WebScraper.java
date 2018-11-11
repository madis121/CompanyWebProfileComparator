package ee.tlu.cwpc.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.tlu.cwpc.dto.WebsiteKeyword;
import ee.tlu.cwpc.helper.StringHelper;
import ee.tlu.cwpc.helper.URLHelper;

/**
 * http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 */
public class WebScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);

	private String website;

	private List<String> websites;

	private Integer maxPagesToSearch;

	private Integer ignoreWordsWithLength;

	private Set<String> ignoredHtmlElements;

	private Set<String> redundantWords;

	private Set<String> pagesVisited = new HashSet<>();

	private Map<String, WebsiteKeyword> keywords = new HashMap<>();

	private int timesScraped;

	public WebScraper(String website, Integer maxPagesToSearch, Integer ignoreWordsWithLength,
			Set<String> ignoredHtmlElements, Set<String> redundantWords) {
		this.website = website;
		this.maxPagesToSearch = maxPagesToSearch;
		this.ignoreWordsWithLength = ignoreWordsWithLength == null ? 0 : ignoreWordsWithLength;
		this.ignoredHtmlElements = ignoredHtmlElements;
		this.redundantWords = redundantWords;
	}

	public WebScraper(List<String> websites, Integer maxPagesToSearch, Integer ignoreWordsWithLength,
			Set<String> ignoredHtmlElements, Set<String> redundantWords) {
		this.websites = websites;
		this.maxPagesToSearch = maxPagesToSearch;
		this.ignoreWordsWithLength = ignoreWordsWithLength == null ? 0 : ignoreWordsWithLength;
		this.ignoredHtmlElements = ignoredHtmlElements;
		this.redundantWords = redundantWords;
	}

	public void collectData() {
		if (website != null) {
			timesScraped = 0;
			search(website);
			LOGGER.debug(String.format("Finished scraping data from %s (scraped %d times)", website, timesScraped));
		}

		if (websites != null) {
			for (String website : websites) {
				timesScraped = 0;
				search(website);
				LOGGER.debug(String.format("Finished scraping data from %s (scraped %d times)", website, timesScraped));
			}
		}
	}

	private void search(String url) {
		timesScraped++;
		List<String> links = new ArrayList<>();
		url = URLHelper.clean(url);
		url = URLHelper.removeParameters(url);

		if (!pagesVisited.contains(url) && !URLHelper.containsHashtag(url)) {
			links = getLinksFoundAtUrl(url);
			LOGGER.trace("Found " + links.size() + " links at " + url);
			getDataFoundAtUrl(url);
		}

		for (String link : links) {
			if (maxPagesToSearch != null && maxPagesToSearch <= timesScraped) {
				return;
			}
			if (!pagesVisited.contains(link) && !URLHelper.containsHashtag(link) && !URLHelper.isMailto(link)) {
				search(link);
			}
		}
	}

	private List<String> getLinksFoundAtUrl(String url) {
		List<String> links = new ArrayList<>();

		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();
			Elements linksOnPage = htmlDocument.select("a[href]");

			for (Element link : linksOnPage) {
				String absUrl = link.absUrl("href");

				if (absUrl.contains(url) && !URLHelper.containsHashtag(absUrl) && !URLHelper.isMailto(absUrl)) {
					links.add(absUrl);
				}
			}

			pagesVisited.add(url);
		} catch (Exception e) {
			LOGGER.error("Encountered an error while collecting links from " + url + ": " + ExceptionUtils.getMessage(e));
		}

		LOGGER.debug(Arrays.toString(links.toArray()));
		return links;
	}

	private void getDataFoundAtUrl(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();

			for (Element element : htmlDocument.body().getAllElements()) {
				if (element.hasText() && !ignoredHtmlElements.contains(element.tagName())) {
					String elementText = element.ownText().toLowerCase();
					String[] words = elementText.split(" ");

					for (String word : words) {
						if (StringUtils.isNotBlank(word) && !word.contains("@")) {
							word = StringHelper.removeNonWordCharacters(word);

							if (StringUtils.isNotBlank(word) && !redundantWords.contains(word)
									&& word.length() > ignoreWordsWithLength && word.matches(".*[a-zA-Z]{2,}.*")) {
								WebsiteKeyword keyword = keywords.getOrDefault(word, new WebsiteKeyword(word, 0));
								keyword.setCount(keyword.getCount() + 1);
								keywords.put(word, keyword);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Encountered an error while collecting data from " + url + ": " + ExceptionUtils.getMessage(e));
		}
	}

	public Map<String, WebsiteKeyword> getKeywords() {
		return keywords;
	}

	public List<WebsiteKeyword> getCommonKeywords() {
		List<WebsiteKeyword> keywordList = new ArrayList<>(keywords.values());
		Collections.sort(keywordList);
		if (keywordList.size() > 50) {
			return keywordList.subList(0, 50);
		}
		return keywordList;
	}

	public List<String> getCommonKeywordStrings() {
		List<String> keywordStrings = new ArrayList<String>();
		for (WebsiteKeyword keyword : getCommonKeywords()) {
			keywordStrings.add(keyword.getWord());
		}
		return keywordStrings;
	}

}
