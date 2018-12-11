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
		return settings;
	}

	@Override
	@Transactional
	public void saveSettings(Settings settings) {
		settingsDAO.save(settings);
	}

	@Override
	@Transactional
	public void updateSettings(Settings settings) {
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
