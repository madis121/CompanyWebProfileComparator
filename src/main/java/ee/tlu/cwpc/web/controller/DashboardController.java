package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.tlu.cwpc.dto.WebsiteData;
import ee.tlu.cwpc.dto.WebsiteKeyword;
import ee.tlu.cwpc.utils.Utils;
import ee.tlu.cwpc.web.WebScraper;

@Controller
@RequestMapping("/")
public class DashboardController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
	
	@Value("${max.pages.to.search:#{null}}")
	private Integer maxPagesToSearch;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String openDashboard(Model model) {
		addPageAttributesToModel(model);
		return "dashboard";
	}

	@RequestMapping(value = "/collect-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<WebsiteData> collectDataFromWebsites(@RequestParam(name = "website") List<String> websites) {
		List<WebsiteData> data = new ArrayList<>();
		
		for (String website : websites) {
			WebScraper webScraper = new WebScraper(null, 2);
			webScraper.search(website);
			List<WebsiteKeyword> keywords = new ArrayList<>(webScraper.getKeywords().values());
			Collections.sort(keywords);
			data.add(new WebsiteData(website, keywords.subList(0, 50)));
			LOGGER.debug("Finished scraping data from " + website);
		}
		
		return data;
	}

}
