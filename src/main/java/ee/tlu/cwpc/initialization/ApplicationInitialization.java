package ee.tlu.cwpc.initialization;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ee.tlu.cwpc.model.Settings;
import ee.tlu.cwpc.service.SettingsService;

@Component
public class ApplicationInitialization {

	@Autowired
	private SettingsService settingsService;

	@PostConstruct
	public void init() {
		Settings settings = settingsService.getSettings();

		if (settings == null) {
			Settings newSettings = new Settings(25, 3, "button,iframe,script", "kui,ning,mis", DateTime.now(),
					DateTime.now());
			settingsService.saveSettings(newSettings);
		}
	}

}
