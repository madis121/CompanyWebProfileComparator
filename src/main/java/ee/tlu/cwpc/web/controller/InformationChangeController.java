package ee.tlu.cwpc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class InformationChangeController extends BaseController {

	@RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
	public String changeLanguage(@RequestParam(name = "languageCode") String languageCode) {
		changeLocale(languageCode);
		return "redirect:" + request.getHeader("Referer");
	}

}
