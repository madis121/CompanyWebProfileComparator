package ee.tlu.cwpc.configuration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageBundle extends ResourceBundleMessageSource {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private SupportedLanguages languages;

  private Map<String, Map<String, String>> messages;

  @PostConstruct
  private void init() {
    messages = new HashMap<>();
    languages.getAll().stream()
        .forEach(language -> messages.put(language,
            getResourceBundle("messages", new Locale(language)).keySet().stream()
                .collect(Collectors.toMap(key -> key,
                    key -> messageSource.getMessage(key, null, "", new Locale(language)),
                    (k1, k2) -> k1))));

  }

  public Map<String, Map<String, String>> getMessages() {
    return messages;
  }

}
