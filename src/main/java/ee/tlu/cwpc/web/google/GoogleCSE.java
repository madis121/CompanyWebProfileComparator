package ee.tlu.cwpc.web.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.tlu.cwpc.dto.CSEObject;

@Component
public class GoogleCSE {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCSE.class);

	@Value("${google.cse.id}")
	private String engineId;

	@Value("${google.cse.api.request.url}")
	private String requestUrl;

	@Value("${google.cse.api.key}")
	private String key;
	
	@Value("${google.cse.api.requests:#{1}}")
	private Integer requests;

	@Autowired
	private MessageSource messageSource;

	private static final HttpClient CLIENT = HttpClientBuilder.create().build();

	private static ObjectMapper mapper = new ObjectMapper();

	public CSEObject requestLinksFromCSE(String query, String countryCode, Locale locale) {
		List<String> items = new ArrayList<>();
		int numOfResults = requests * 10 + 1;

		try {
			for (int i = 1; i <= numOfResults; i = i + 10) {
				String url = requestUrl.concat("?q=").concat(URLEncoder.encode(query, StandardCharsets.UTF_8.name()))
						.concat("&cr=country").concat(countryCode).concat("&start=").concat(String.valueOf(i)).concat("&key=")
						.concat(key).concat("&cx=").concat(engineId);
				HttpGet request = new HttpGet(url);
				HttpResponse response = CLIENT.execute(request);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200) {
					if (statusCode == 403) {
						LOGGER.warn("API daily limit has exceeded.");
						return new CSEObject(HttpStatus.FORBIDDEN,
								messageSource.getMessage("companySearch.result.error.403", null, locale));
					}

					LOGGER.error("Received an error while using CSE");
					return new CSEObject(HttpStatus.BAD_REQUEST,
							messageSource.getMessage("companySearch.result.error.400", null, locale));
				}

				StringBuffer result = getResult(response);
				CSEResponse cseResponse = mapper.readValue(result.toString(), CSEResponse.class);

				for (CSEItem item : cseResponse.getItems()) {
					String protocol = item.getLink().startsWith("https://") ? "https://" : "http://";
					String link = protocol.concat(item.getDisplayLink());
					
					if (!items.contains(link)) {
						items.add(link);
					}
				}

				request.releaseConnection();
			}
		} catch (Exception e) {
			LOGGER.error("Encountered an unexpected error while using CSE: ", e);
		}

		return new CSEObject(HttpStatus.OK, items);
	}

	private StringBuffer getResult(HttpResponse response) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";

			while ((line = reader.readLine()) != null) {
				result.append(line);
			}

			return result;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

}
