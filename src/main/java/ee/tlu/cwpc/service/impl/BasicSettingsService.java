package ee.tlu.cwpc.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.tlu.cwpc.dao.SettingsDAO;
import ee.tlu.cwpc.model.Settings;
import ee.tlu.cwpc.service.SettingsService;

@Service("settingsService")
public class BasicSettingsService implements SettingsService {

	@Autowired
	private SettingsDAO settingsDAO;

	@Override
	@Transactional
	public Settings getSettings() {
		Settings settings = settingsDAO.findOne(1);
		if (settings == null) {
			return new Settings(25, 3, "button,iframe,script", "kui,ning,mis", DateTime.now(), DateTime.now());
		}
		return settings;
	}

	@Override
	@Transactional
	public void saveSettings(Settings settings) {
		if (settings.getId() == 0L) {
			settingsDAO.save(settings);
		} else {
			if (settings.getWebScraperIgnoredHTMLElements().contains(" ")) {
				String cleaned = settings.getWebScraperIgnoredHTMLElements().replaceAll(" ", "");
				settings.setWebScraperIgnoredHTMLElements(cleaned);
			}

			if (settings.getWebScraperIgnoredKeywords().contains(" ")) {
				String cleaned = settings.getWebScraperIgnoredKeywords().replaceAll(" ", "");
				settings.setWebScraperIgnoredKeywords(cleaned);
			}

			settings.setUpdated(DateTime.now());
			settingsDAO.update(settings);
		}
	}

}
