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
import ee.tlu.cwpc.helper.URLHelper;

/**
 * http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 */
public class WebScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);
	
	private Integer maxPagesToSearch;
	
	private Integer ignoreWordsWithLength;

	private Set<String> pagesVisited = new HashSet<>();

	private Map<String, WebsiteKeyword> keywords = new HashMap<>();

	public WebScraper(Integer maxPagesToSearch, Integer ignoreWordsWithLength) {
		this.maxPagesToSearch = maxPagesToSearch;
		this.ignoreWordsWithLength = ignoreWordsWithLength == null ? 0 : ignoreWordsWithLength;
	}

	public void search(String url) {
		List<String> links = new ArrayList<>();
		url = URLHelper.clean(url);
		url = URLHelper.removeParameters(url);
		
		if (!pagesVisited.contains(url) && !URLHelper.containsHashtag(url)) {
			links = getLinksFoundAtUrl(url);
			LOGGER.debug("Found " + links.size() + " links at " + url);
			getDataFoundAtUrl(url);
		}
		
		//maxPagesToSearch = maxPagesToSearch != null ? maxPagesToSearch : links.size();

		for (String link : links) {
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

			for (Element element : htmlDocument.getAllElements()) {
				if (element.children().isEmpty() && element.hasText() && !ignoredElement(element.tagName())) {
					String[] wordsInElement = element.text().split(" ");

					for (String word : wordsInElement) {
						word = URLHelper.removeNonWordCharacters(word).toLowerCase();

						if (!StringUtils.isBlank(word) && word.length() > ignoreWordsWithLength) {
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
