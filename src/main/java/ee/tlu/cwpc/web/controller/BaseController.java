package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

public class BaseController {
	
	@Autowired
	protected MessageSource messageSource;
	
	public Model addPageAttributesToModel(Model model) {
		model.addAttribute("supportedLanguages", getSupportedLanguages());
		model.addAttribute("currentLanguage", getCurrentLanguage());
		model.addAttribute("title", messageSource.getMessage("common.title", null, getLocale()));
		return model;
	}
	
	public List<String> getSupportedLanguages() {
		List<String> languages = new ArrayList<>();
		Collections.addAll(languages, "et", "en");
		return languages;
	}
	
	public String getCurrentLanguage() {
		return getLocale().getLanguage();
	}
	
	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

}
