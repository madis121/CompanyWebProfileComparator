package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.tlu.cwpc.dto.WebsiteData;
import ee.tlu.cwpc.web.WebScraper;

@Controller
@RequestMapping("/")
public class DashboardController {
	
	@Value("${max.pages.to.search:#{null}}")
	private Integer maxPagesToSearch;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String openDashboard(HttpSession session, Model model) {
		return "dashboard";
	}

	@RequestMapping(value = "/collect-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<WebsiteData> collectDataFromWebsites(@RequestParam(name = "website") List<String> websites, HttpSession session) {
		List<WebsiteData> data = new ArrayList<>();
		
		for (String website : websites) {
			WebScraper webScraper = new WebScraper();
			webScraper.search(website, maxPagesToSearch);
			Map<String, Integer> collectedWords = webScraper.getCollectedWords();
			data.add(new WebsiteData(website, new ArrayList<>(collectedWords.keySet())));
		}
		
		return data;
	}

}
