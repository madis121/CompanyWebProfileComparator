package ee.tlu.cwpc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleScraper.class);

	public List<String> getLinks(String url) {
		List<String> links = new ArrayList<>();

		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();
			Elements linksOnPage = htmlDocument.select("div#search a[href]");

			for (Element link : linksOnPage) {
				String absUrl = link.absUrl("href");

				if (StringUtils.isNotBlank(absUrl) && !absUrl.contains("google")) {
					links.add(absUrl);
				}
			}
		} catch (IOException e) {
			LOGGER.error("Encountered an error while collecting links from Google: ", e);
		}

		return links;
	}

}
