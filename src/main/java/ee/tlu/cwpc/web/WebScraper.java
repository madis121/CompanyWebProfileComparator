package ee.tlu.cwpc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 */
public class WebScraper {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebScraper.class);
	
	private Set<String> pagesVisited = new HashSet<>();

	public void search(String url) {
		List<String> links = getLinksFoundAtUrl(url);
		LOGGER.debug("Links found at " + url + ":\n" + Arrays.toString(links.toArray()));
		
		for (String link : links) {
			if (!pagesVisited.contains(link)) {
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
			e.printStackTrace();
		}
		return links;
	}

}
