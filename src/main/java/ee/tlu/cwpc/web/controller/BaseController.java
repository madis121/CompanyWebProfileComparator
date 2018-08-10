package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

public class BaseController {

	public Model addPageAttributesToModel(Model model) {
		model.addAttribute("supportedLanguages", getSupportedLanguages());
		model.addAttribute("currentLanguage", getCurrentLanguage());
		return model;
	}
	
	public List<String> getSupportedLanguages() {
		List<String> languages = new ArrayList<>();
		Collections.addAll(languages, "et", "en");
		return languages;
	}
	
	public String getCurrentLanguage() {
		return LocaleContextHolder.getLocale().getLanguage();
	}

}
