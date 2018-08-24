package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.web.WebScraper;

@Controller
public class DashboardController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@Value("${max.pages.to.search:#{null}}")
	private Integer maxPagesToSearch;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String openDashboard(@RequestParam(name = "action", required = false, defaultValue = "") String action,
			@RequestParam(name = "hasErrors", required = false, defaultValue = "false") boolean hasErrors, Model model) {
		List<Profile> profiles = profileService.getProfiles();
		model.addAttribute("profiles", profiles);
		model.addAttribute("action", action);
		model.addAttribute("hasErrors", hasErrors);
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

	@RequestMapping(value = "/create-profile", method = RequestMethod.POST)
	public String createProfile(@RequestParam(name = "name") String name, @RequestParam(name = "urls") List<String> urls,
			@RequestParam(name = "keywords") List<String> keywords) {
		profileService.createProfile(name, urls, keywords);
		return "redirect:/?action=profileSaved";
	}

	@RequestMapping(value = "/open-profile", method = RequestMethod.GET)
	public String openProfile(@RequestParam(name = "id") long id, Model model) {
		Profile profile = profileService.getProfile(id);
		List<String> keywords = profileService.getProfileKeywords(id);
		model.addAttribute("profile", profile);
		model.addAttribute("commaSeperatedKeywords", StringUtils.join(keywords, ","));
		return "profileModal";
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	public String updateProfile(@RequestParam(name = "id") long id, @RequestParam(name = "name") String name,
			@RequestParam(name = "keywords") List<String> keywords) {
		profileService.updateProfile(id, name, keywords);
		return "redirect:/?action=profileUpdated";
	}

	@RequestMapping(value = "/delete-profile-alert", method = RequestMethod.GET)
	public String deleteProfileAlert(@RequestParam(name = "id") long id, Model model) {
		Profile profile = profileService.getProfile(id);
		model.addAttribute("profile", profile);
		return "deleteProfileAlert";
	}

	@RequestMapping(value = "/delete-profile", method = RequestMethod.POST)
	public String deleteProfile(@RequestParam(name = "id") long id) {
		profileService.deleteProfile(id);
		return "redirect:/?action=profileDeleted";
	}

}
