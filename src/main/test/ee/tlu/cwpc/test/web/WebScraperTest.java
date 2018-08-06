package ee.tlu.cwpc.test.web;

import org.junit.jupiter.api.Test;

import ee.tlu.cwpc.web.WebScraper;

class WebScraperTest {

	private WebScraper webScraper = new WebScraper();
	
	@Test
	void test() {
		System.out.println(webScraper.urlContainsHashtag("http://www.premia.ee/kontakt/"));
	}

}
