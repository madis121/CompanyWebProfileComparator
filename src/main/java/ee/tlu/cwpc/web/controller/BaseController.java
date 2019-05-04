package ee.tlu.cwpc.web.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import ee.tlu.cwpc.configuration.SupportedLanguages;
import ee.tlu.cwpc.service.ProfileService;

public class BaseController {

  @Autowired
  protected ProfileService profileService;

  @Autowired
  protected LocaleResolver localeResolver;

  @Autowired
  protected MessageSource messageSource;

  @Autowired
  protected HttpServletRequest request;

  @Autowired
  protected HttpServletResponse response;

  @Autowired
  private SupportedLanguages languages;

  public Model addPageAttributesToModel(Model model) {
    model.addAttribute("supportedLanguages", getSupportedLanguages());
    model.addAttribute("currentLanguage", getCurrentLanguage());
    model.addAttribute("title", messageSource.getMessage("common.title", null, getLocale()));
    return model;
  }

  public List<String> getSupportedLanguages() {
    return languages.getAll();
  }

  public String getCurrentLanguage() {
    return getLocale().getLanguage();
  }

  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

  protected void changeLocale(String languageCode) {
    Locale locale = StringUtils.parseLocaleString(languageCode);
    localeResolver.setLocale(request, response, locale);
  }

}
