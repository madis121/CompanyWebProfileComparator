package ee.tlu.cwpc.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
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
    List<ProfileDTO> profiles = profileService.getProfiles();
    map.put("profiles", profiles);
    return map;
  }

  @RequestMapping(value = "/getProfile", method = GET, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getProfile(@RequestParam(name = "id") long id) {
    Map<String, Object> map = new HashMap<>();
    ProfileDTO profile = profileService.getProfile(id);
    profile.setKeywords(StringUtils.join(profileService.getKeywordsByProfileId(id), ","));
    map.put("profile", profile);
    return map;
  }

  @RequestMapping(value = "/createProfile", method = POST, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> createProfile(@RequestParam(name = "name") String name,
      @RequestParam(name = "urls") List<String> urls,
      @RequestParam(name = "keywords") List<String> keywords) {
    Map<String, Object> map = new HashMap<>();
    profileService.createProfile(name, urls, keywords);
    map.put("response", "OK");
    return map;
  }

  @RequestMapping(value = "/updateProfile", method = POST, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> updateProfile(@RequestParam(name = "id") long id,
      @RequestParam(name = "name") String name,
      @RequestParam(name = "keywords") List<String> keywords) {
    Map<String, Object> map = new HashMap<>();
    profileService.updateProfile(id, name, keywords);
    map.put("response", "OK");
    return map;
  }

  @RequestMapping(value = "/deleteProfile", method = POST, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> deleteProfile(@RequestParam(name = "id") long id) {
    Map<String, Object> map = new HashMap<>();
    profileService.deleteProfile(id);
    map.put("response", "OK");
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
