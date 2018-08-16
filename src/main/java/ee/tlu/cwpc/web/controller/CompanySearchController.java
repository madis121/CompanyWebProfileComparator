package ee.tlu.cwpc.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		model.addAttribute("profile", profile);
		addPageAttributesToModel(model);
		return "companySearchDetails";
	}
	
}
