package ee.tlu.cwpc.web;

import java.io.IOException;
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

public class WebScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);

	private String website;

	private List<String> websites;

	private int maxPagesToSearch;

	private int minKeywordLength;

	private Set<String> ignoredHTMLElements;

	private Set<String> ignoredKeywords;

	private int timesScraped;

	private Set<String> visitedWebPages = new HashSet<>();

	private Map<String, WebsiteKeyword> keywords = new HashMap<>();

	public WebScraper(String website, int maxPagesToSearch, int minKeywordLength, Set<String> ignoredHTMLElements,
			Set<String> ignoredKeywords) {
		this.website = website;
		this.maxPagesToSearch = maxPagesToSearch < 10 ? 10 : maxPagesToSearch;
		this.minKeywordLength = minKeywordLength;
		this.ignoredHTMLElements = ignoredHTMLElements == null ? new HashSet<>() : ignoredHTMLElements;
		this.ignoredKeywords = ignoredKeywords == null ? new HashSet<>() : ignoredKeywords;
	}

	public WebScraper(List<String> websites, int maxPagesToSearch, int minKeywordLength, Set<String> ignoredHTMLElements,
			Set<String> ignoredKeywords) {
		this.websites = websites;
		this.maxPagesToSearch = maxPagesToSearch < 10 ? 10 : maxPagesToSearch;
		this.minKeywordLength = minKeywordLength;
		this.ignoredHTMLElements = ignoredHTMLElements == null ? new HashSet<>() : ignoredHTMLElements;
		this.ignoredKeywords = ignoredKeywords == null ? new HashSet<>() : ignoredKeywords;
	}

	public void scrape() {
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
		List<String> links = null;
		url = URLHelper.clean(url);
		url = URLHelper.removeParameters(url);

		if (!url.substring(url.length() - 1).equals("/")) {
			url = url.concat("/");
		}

		if (!visitedWebPages.contains(url)) {
			visitedWebPages.add(url);
			Document html = getHTMLDocument(url);
			links = getLinks(url, html);
			collectData(url, html);
		}

		if (links != null) {
			for (String link : links) {
				if (maxPagesToSearch <= timesScraped) {
					return;
				}

				if (!visitedWebPages.contains(link)) {
					search(link);
				}
			}
		}
	}

	private Document getHTMLDocument(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document html = connection.get();
			return html;
		} catch (IOException e) {
			LOGGER.error(
					String.format("Encountered an error while accessing website %s: %s", url, ExceptionUtils.getMessage(e)));
		}

		return null;
	}

	private List<String> getLinks(String url, Document html) {
		List<String> unvisitedLinks = new ArrayList<>();

		if (html != null) {
			Elements linkElements = html.select("a[href]");

			for (Element linkElement : linkElements) {
				String linkURL = linkElement.absUrl("href");
				linkURL = URLHelper.clean(linkURL);
				linkURL = URLHelper.removeParameters(linkURL);

				if (!visitedWebPages.contains(linkURL) && linkURL.contains(url) && !URLHelper.isMailto(linkURL)) {
					unvisitedLinks.add(linkURL);
				}
			}

			LOGGER.trace(String.format("Found %d new links at %s", unvisitedLinks.size(), url));
			LOGGER.trace(String.format("Links: %s", Arrays.toString(unvisitedLinks.toArray())));
		}

		return unvisitedLinks;
	}

	private void collectData(String url, Document html) {
		if (html != null) {
			Elements elements = html.body().getAllElements();
			//Elements elements = html.body().getElementsByTag("p");
			
			for (Element element : elements) {
				if (!ignoredHTMLElements.contains(element.tagName()) && element.hasText()) {
					String elementText = element.ownText().toLowerCase();
					String[] words = elementText.split(" ");

					for (String word : words) {
						if (StringUtils.isNotBlank(word) && !word.contains("@")) {
							word = StringHelper.removeNonWordCharacters(word);

							if (StringUtils.isNotBlank(word) && word.matches(".*[a-zA-Z]{2,}.*") && word.length() >= minKeywordLength
									&& !ignoredKeywords.contains(word)) {
								WebsiteKeyword keyword = keywords.getOrDefault(word, new WebsiteKeyword(word, 0));
								keyword.setCount(keyword.getCount() + 1);
								keywords.put(word, keyword);
							}
						}
					}
				}
			}
		}
	}

	public Map<String, WebsiteKeyword> getKeywords() {
		return keywords;
	}

	public List<WebsiteKeyword> getCommonKeywords() {
		List<WebsiteKeyword> keywordList = new ArrayList<>(keywords.values());
		Collections.sort(keywordList);

		if (keywordList.size() > 25) {
			return keywordList.subList(0, 25);
		}

		return keywordList;
	}

	public List<String> getCommonKeywordStrings() {
		List<String> keywordStringList = new ArrayList<String>();

		for (WebsiteKeyword keyword : getCommonKeywords()) {
			keywordStringList.add(keyword.getWord());
		}

		return keywordStringList;
	}

}
