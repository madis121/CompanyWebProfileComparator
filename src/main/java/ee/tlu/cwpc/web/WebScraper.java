package ee.tlu.cwpc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.tlu.cwpc.dto.PageData;
import ee.tlu.cwpc.dto.PageElement;

/**
 * http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 */
public class WebScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);

	private Set<String> pagesVisited = new HashSet<>();

	private List<PageData> collectedData = new ArrayList<>();

	private Map<String, Integer> words = new HashMap<>();

	public List<PageData> search(String url) {
		List<String> links = getLinksFoundAtUrl(url);
		LOGGER.debug("Found " + links.size() + " links at " + url);
		collectedData.add(getDataFoundAtUrl(url));

		for (String link : links) {
			if (!pagesVisited.contains(link) && !urlContainsHashtag(link)) {
				search(link);
			}
		}

		return collectedData;
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

	private PageData getDataFoundAtUrl(String url) {
		List<PageElement> pageElements = new ArrayList<>();

		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();

			for (Element element : htmlDocument.getAllElements()) {
				if (element.hasText() && !ignoredElement(element.tagName())) {
					pageElements.add(new PageElement(element.tagName(), element.text()));
				}
			}
		} catch (IOException e) {
			LOGGER.error("Encountered an error while collecting data: ", e);
		}

		return new PageData(url, pageElements);
	}

	public boolean urlContainsHashtag(String url) {
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}

		if (url.contains("#")) {
			return true;
		}
		return false;
	}

	private boolean ignoredElement(String tagName) {
		Set<String> set = new HashSet<>();
		String[] ignoredElementTags = { "html", "head", "title", "body", "a", "button" };
		Collections.addAll(set, ignoredElementTags);
		return set.contains(tagName);
	}

	public Map<String, Integer> getWords() {
		return words;
	}

}
