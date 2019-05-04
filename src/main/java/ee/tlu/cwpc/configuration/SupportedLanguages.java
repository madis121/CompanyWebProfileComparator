package ee.tlu.cwpc.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SupportedLanguages {

  public List<String> getAll() {
    List<String> languages = new ArrayList<>();
    Collections.addAll(languages, "et", "en");
    return languages;
  }

}
