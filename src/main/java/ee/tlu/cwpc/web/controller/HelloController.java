package ee.tlu.cwpc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hello(Model model) {
		model.addAttribute("index", "index");
		return "hello";
	}
	
}
