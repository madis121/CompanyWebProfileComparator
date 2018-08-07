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

import ee.tlu.cwpc.utils.Utils;

/**
 * http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 */
public class WebScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);

	private Set<String> pagesVisited = new HashSet<>();

	private Map<String, Integer> collectedWords = new HashMap<>();

	public void search(String url, Integer maxPagesToSearch) {
		List<String> links = getLinksFoundAtUrl(url);
		LOGGER.debug("Found " + links.size() + " links at " + url);
		getDataFoundAtUrl(url);

		maxPagesToSearch = maxPagesToSearch != null ? maxPagesToSearch : links.size();

		for (String link : links) {
			if (!pagesVisited.contains(link) && !Utils.urlContainsHashtag(link)) {
				search(link, maxPagesToSearch);
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
						word = Utils.removeNonWordCharacters(word).toLowerCase();

						if (!StringUtils.isBlank(word)) {
							collectedWords.put(word, collectedWords.getOrDefault(word, 0) + 1);
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

	public Map<String, Integer> getCollectedWords() {
		return collectedWords;
	}

}
