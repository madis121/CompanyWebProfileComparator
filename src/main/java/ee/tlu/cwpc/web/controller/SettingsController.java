package ee.tlu.cwpc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.tlu.cwpc.model.Settings;
import ee.tlu.cwpc.service.SettingsService;

@Controller
@RequestMapping("settings")
public class SettingsController extends BaseController {

	@Autowired
	private SettingsService settingsService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String openSettings(Model model) {
		Settings settings = settingsService.getSettings();
		model.addAttribute("settings", settings);
		addPageAttributesToModel(model);
		return "settings";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String saveSettings(@ModelAttribute("settings") Settings settings, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/error";
		}

		settingsService.updateSettings(settings);
		return "redirect:/settings";
	}

}
