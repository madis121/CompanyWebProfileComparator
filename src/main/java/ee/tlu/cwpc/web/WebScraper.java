package ee.tlu.cwpc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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

	private List<String> websites;

	private Integer maxPagesToSearch;

	private Integer ignoreWordsWithLength;

	private Set<String> pagesVisited = new HashSet<>();

	private Map<String, WebsiteKeyword> keywords = new HashMap<>();

	private int timesScraped;

	private Set<String> redundantWords;

	public WebScraper(List<String> websites, Integer maxPagesToSearch, Integer ignoreWordsWithLength,
			Set<String> redundantWords) {
		this.websites = websites;
		this.maxPagesToSearch = maxPagesToSearch;
		this.ignoreWordsWithLength = ignoreWordsWithLength == null ? 0 : ignoreWordsWithLength;
		this.redundantWords = redundantWords;
	}

	public void collectData() {
		for (String website : websites) {
			timesScraped = 0;
			search(website);
			LOGGER.debug(String.format("Finished scraping data from %s (scraped %d times)", website, timesScraped));
		}
	}

	public void search(String url) {
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
			if (!pagesVisited.contains(link) && !URLHelper.containsHashtag(link)) {
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

				if (absUrl.contains(url)) {
					links.add(absUrl);
				}
			}

			pagesVisited.add(url);
		} catch (IOException e) {
			LOGGER.error("Encountered an error while collecting links: ", e);
		}

		return links;
	}

	private void getDataFoundAtUrl(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();

			for (Element element : htmlDocument.body().getAllElements()) {
				String elementText = element.ownText().toLowerCase();
				String[] words = elementText.split(" ");

				for (String word : words) {
					if (StringUtils.isNotBlank(word) && !word.contains("@")) {
						word = StringHelper.removeNonWordCharacters(word);

						if (StringUtils.isNotBlank(word) && !redundantWords.contains(word) && word.length() > ignoreWordsWithLength
								&& word.matches(".*[a-zA-Z]{2,}.*")) {
							WebsiteKeyword keyword = keywords.getOrDefault(word, new WebsiteKeyword(word, 0));
							keyword.setCount(keyword.getCount() + 1);
							keywords.put(word, keyword);
						}
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Encountered an error while collecting data: ", e);
		}
	}

	private boolean ignoredElement(String tagName) {
		Set<String> set = new HashSet<>();
		String[] ignoredElementTags = { "html", "head", "title", "body", "a", "button", "iframe" };
		Collections.addAll(set, ignoredElementTags);
		return set.contains(tagName);
	}

	public Map<String, WebsiteKeyword> getKeywords() {
		return keywords;
	}

}
