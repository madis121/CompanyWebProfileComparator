package ee.tlu.cwpc.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ee.tlu.cwpc.dto.PageData;
import ee.tlu.cwpc.web.WebScraper;

@Controller
@RequestMapping("/")
public class DashboardController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String openDashboard(HttpSession session, Model model) {
		List<String> websites = (List<String>) session.getAttribute("websites");
		model.addAttribute("processedWebsites", websites);
		return "dashboard";
	}

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String websitesToProcess(@RequestParam(name = "website") List<String> websites, HttpSession session) {
		session.setAttribute("websites", websites);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/scrape", method = RequestMethod.GET)
	public String scrape(HttpSession session) {
		WebScraper webScraper = new WebScraper();
		List<PageData> data = webScraper.search("http://www.premia.ee/");
		return "redirect:/";
	}

}
