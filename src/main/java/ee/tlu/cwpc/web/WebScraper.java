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

/**
 * http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
 */
public class WebScraper {

	private static final int MAX_PAGES_TO_SEARCH = 10;

	private List<String> pagesToVisit = new ArrayList<>();
	
	private Set<String> pagesVisited = new HashSet<>();

	private String nextUrl() {
		String nextUrl;

		for (String page : pagesToVisit) {
			nextUrl = pagesToVisit.remove(0);
		}
		
		do {
			nextUrl = pagesToVisit.remove(0);
		} while (pagesToVisit.contains(nextUrl));

		pagesVisited.add(nextUrl);
		return nextUrl;
	}

	public void search(String url, String searchWord) {
//		while (pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
//			String currentUrl;
//			Scraper scraper = new Scraper();
//
//			if (pagesToVisit.isEmpty()) {
//				currentUrl = url;
//				pagesVisited.add(url);
//			} else {
//				currentUrl = this.nextUrl();
//			}
//
//			System.out.println("currentUrl: " + currentUrl);
//			scraper.crawl(currentUrl);
//			boolean success = scraper.searchForWord(searchWord);
//
//			if (success) {
//				System.out.println(String.format("SUCCESS: Word %s found at %s", searchWord, currentUrl));
//				break;
//			}
//
//			pagesToVisit.addAll(scraper.getLinks());
//		}
//
//		System.out.println(String.format("DONE: Visited %s web page(s)", pagesVisited.size()));
		WebCrawler webCrawler = new WebCrawler();
		webCrawler.crawl(url);
		List<String> links = webCrawler.getLinks();
		System.out.println(Arrays.toString(links.toArray()));
	}

	private class WebCrawler {

		private List<String> links = new ArrayList<>();

		private Document htmlDocument;

		public void crawl(String url) {
			try {
				Connection connection = Jsoup.connect(url);
				Document htmlDocument = connection.get();
				this.htmlDocument = htmlDocument;

				System.out.println("Received web page at " + url);

				Elements linksOnPage = htmlDocument.select("a[href]");
				System.out.println("Found (" + linksOnPage.size() + ") links");

				for (Element link : linksOnPage) {
					String absUrl = link.absUrl("href");
					
					if (absUrl.contains(url)) {
						links.add(absUrl);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public boolean searchForWord(String word) {
			System.out.println("Searching for the word " + word + "...");
			String bodyText = htmlDocument.body().text();
			return bodyText.toLowerCase().contains(word.toLowerCase());
		}

		public List<String> getLinks() {
			return this.links;
		}

	}

}
