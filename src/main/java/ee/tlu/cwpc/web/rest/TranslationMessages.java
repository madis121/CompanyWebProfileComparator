package ee.tlu.cwpc.web.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ee.tlu.cwpc.configuration.MessageBundle;
import ee.tlu.cwpc.web.controller.BaseController;

@Controller
public class TranslationMessages extends BaseController {

  @Autowired
  private MessageBundle messageBundle;

  @RequestMapping(value = "/messageBundle", method = GET, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, String> getAll() {
    return messageBundle.getMessages().get(getCurrentLanguage());
  }

}
