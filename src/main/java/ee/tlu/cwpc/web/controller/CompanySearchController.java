package ee.tlu.cwpc.web.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.tlu.cwpc.helper.Countries;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.web.google.GoogleCSE;

@Controller
@RequestMapping("company-search")
public class CompanySearchController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanySearchController.class);

	@Autowired
	private GoogleCSE googleCSE;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String openCompanySearch(Model model) {
		List<Profile> profiles = profileService.getProfiles();
		model.addAttribute("profiles", profiles);
		addPageAttributesToModel(model);
		return "companySearch";
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String openCompanySearchDetails(@RequestParam(name = "profileId") long profileId, Model model) {
		Profile profile = profileService.getProfile(profileId);
		List<String> keywords = profileService.getProfileKeywords(profileId);
		List<String> urls = profileService.getProfileURLs(profileId);
		model.addAttribute("profile", profile);
		model.addAttribute("commaSeperatedKeywords", StringUtils.join(keywords, ","));
		model.addAttribute("commaSeperatedURLs", StringUtils.join(urls, ","));
		model.addAttribute("countries", Countries.listAll());
		addPageAttributesToModel(model);
		return "companySearchDetails";
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<String>> findSimilarCompanies(@RequestParam(name = "keywords") List<String> keywords,
			@RequestParam(name = "urls") List<String> urls, @RequestParam(name = "country") String countryCode,
			@RequestParam(name = "contacts") List<String> contacts) {
		List<String> links = googleCSE.requestLinksFromCSE(StringUtils.join(keywords, "+"), countryCode, 2);

		if (links == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (links.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		LOGGER.debug("Received links from Google: " + Arrays.toString(links.toArray()));
		return new ResponseEntity<>(links, HttpStatus.OK);
	}

}
