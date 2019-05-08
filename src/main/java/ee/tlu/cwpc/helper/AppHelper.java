package ee.tlu.cwpc.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ee.tlu.cwpc.web.WebScraper;

//TODO: This class is only used for testing, remove this later
public class AppHelper {

	public static void main(String[] args) {
		// System.out.println(URLHelper.clean("https://www.premia.ee/jaatised/regatt/?t=left"));
		// System.out.println(URLHelper.removeParameters("https://www.premia.ee/jaatised/regatt/?t=left"));
		// System.out.println(URLHelper.clean("https://www.premia.ee/jaatised/regatt/###"));

		List<String> websites = new ArrayList<>();
		//websites.add("https://www.premia.ee");
		// websites.add("https://www.balbiino.ee/en/");
		websites.add("http://www.harjuelekter.ee/et");

		WebScraper webScraper = new WebScraper(websites, 25, 3, null, null);
		webScraper.scrape();
		List<String> commonKeywords = webScraper.getMostCommonKeywordAsStrings();
		System.out.println(Arrays.toString(commonKeywords.toArray()));
	}

}
