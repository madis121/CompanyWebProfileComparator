package ee.tlu.cwpc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController extends BaseController {

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String openDashboard(Model model) {
    addPageAttributesToModel(model);
    return "dashboard";
  }

}
