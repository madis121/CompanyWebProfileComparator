package ee.tlu.cwpc.service;

import ee.tlu.cwpc.model.Settings;

public interface SettingsService {

	Settings getSettings();
	
	void saveSettings(Settings settings);
	
}
