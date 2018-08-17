package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.tlu.cwpc.helper.Countries;
import ee.tlu.cwpc.model.Profile;

@Controller
@RequestMapping("company-search")
public class CompanySearchController extends BaseController {

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
		model.addAttribute("profile", profile);
		model.addAttribute("commaSeperatedKeywords", StringUtils.join(keywords, ","));
		model.addAttribute("countries", Countries.listAll());
		addPageAttributesToModel(model);
		return "companySearchDetails";
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	@ResponseBody
	public List<String> findSimilarCompanies(@RequestParam(name = "keywords") List<String> keywords,
			@RequestParam(name = "country") String country, @RequestParam(name = "contacts") List<String> contacts) {
		List<String> data = new ArrayList<>();
		return data;
	}

}
