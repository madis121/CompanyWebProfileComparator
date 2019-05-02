package ee.tlu.cwpc.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ee.tlu.cwpc.dto.CollectedData;
import ee.tlu.cwpc.dto.ProfileDTO;
import ee.tlu.cwpc.helper.StringHelper;
import ee.tlu.cwpc.model.Settings;
import ee.tlu.cwpc.service.ProfileService;
import ee.tlu.cwpc.service.SettingsService;
import ee.tlu.cwpc.web.WebScraper;

@Controller
@RequestMapping("dashboard")
public class ProfileController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

  @Autowired
  protected ProfileService profileService;

  @Autowired
  private SettingsService settingsService;

  @RequestMapping(value = "/getProfiles", method = GET, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getProfiles() {
    Map<String, Object> map = new HashMap<>();
    List<ProfileDTO> profiles = profileService.getProfiles2();
    map.put("profiles", profiles);
    return map;
  }

  @RequestMapping(value = "/collectData", method = GET, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> collectDataFromWebsites(
      @RequestParam(name = "website") List<String> websites) {
    Map<String, Object> map = new HashMap<>();
    Settings settings = settingsService.getSettings();
    Set<String> ignoredHTMLElements =
        StringHelper.splitStringToSet(settings.getWebScraperIgnoredHTMLElements(), ",");
    Set<String> ignoredKeywords =
        StringHelper.splitStringToSet(settings.getWebScraperIgnoredKeywords(), ",");

    LOGGER.info(String.format("Scraping data from %s", Arrays.toString(websites.toArray())));
    WebScraper webScraper = new WebScraper(websites, settings.getWebScraperMaxPagesToSearch(),
        settings.getWebScraperMinKeywordLength(), ignoredHTMLElements, ignoredKeywords);
    webScraper.scrape();
    LOGGER.info("Data scraping done");
    map.put("collectedData", new CollectedData(websites, webScraper.getCommonKeywords()));
    return map;
  }

}
