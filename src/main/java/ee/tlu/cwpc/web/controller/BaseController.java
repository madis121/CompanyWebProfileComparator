package ee.tlu.cwpc.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import ee.tlu.cwpc.helper.URLHelper;
import ee.tlu.cwpc.service.ProfileService;

public class BaseController {

	@Autowired
	protected ProfileService profileService;

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	public Model addPageAttributesToModel(Model model) {
		model.addAttribute("supportedLanguages", getSupportedLanguages());
		model.addAttribute("currentLanguage", getCurrentLanguage());
		model.addAttribute("title", messageSource.getMessage("common.title", null, getLocale()));
		model.addAttribute("currentURL", getCurrentURLWithoutLanguageParameter());
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

	public String getCurrentURL() {
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			return requestURI.concat("?").concat(queryString);
		}
		return requestURI;
	}

	public String getCurrentURLWithoutLanguageParameter() {
		return URLHelper.removeQueryParameter(getCurrentURL(), "language");
	}

}
