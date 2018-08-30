package ee.tlu.cwpc.web.google;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Custom Search Engine - https://cse.google.com/cse/setup/basic?cx=009007231010836904126%3Ac2zzkw4lqci
 * CSE JSON API - https://developers.google.com/custom-search/json-api/v1/reference/cse/list#try-it
 */
@Service
public class GoogleCSE {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCSE.class);

	@Value("${google.custom.search.engine.api.id}")
	private String apiId;

	@Value("${google.custom.search.engine.api.url}")
	private String apiUrl;

	@Value("${google.custom.search.engine.api.key}")
	private String apiKey;

	private static final HttpClient CLIENT = HttpClientBuilder.create().build();

	private static ObjectMapper mapper = new ObjectMapper();

	public List<String> requestLinksFromCSE(String query, String countryCode, int pages) {
		List<String> items = new ArrayList<>();
		int numOfResults = pages * 10 + 1;

		try {
			for (int i = 1; i <= numOfResults; i = i + 10) {
				String url = apiUrl.concat("?q=").concat(query).concat("&cr=country").concat(countryCode).concat("&start=")
						.concat(String.valueOf(i)).concat("&key=").concat(apiKey).concat("&cx=").concat(apiId);
				HttpGet request = new HttpGet(url);
				HttpResponse response = CLIENT.execute(request);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200) {
					if (statusCode == 403) {
						LOGGER.warn("API daily limit has exceeded (100 queries per day).");
						return Collections.emptyList();
					}
					
					LOGGER.debug("Received an error while using CSE");
					return null;
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
		
		return items;
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
