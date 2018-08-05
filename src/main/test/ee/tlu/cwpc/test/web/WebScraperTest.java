package ee.tlu.cwpc.test.web;

import org.junit.jupiter.api.Test;

import ee.tlu.cwpc.web.WebScraper;

class WebScraperTest {

	private WebScraper webScraper = new WebScraper();
	
	@Test
	void test() {
		webScraper.search("http://www.premia.ee/", "Kontakt");
	}

}
