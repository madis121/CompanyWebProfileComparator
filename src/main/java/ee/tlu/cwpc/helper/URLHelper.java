package ee.tlu.cwpc.helper;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLHelper.class);
	
	public static String clean(String url) {
		int lastSlash = url.lastIndexOf("/");
		int urlLength = url.length();

		if (lastSlash < urlLength) {
			String urlTail = url.substring(lastSlash + 1, urlLength);
			String regex = "[\"<>#%{}|\\\\^~\\[\\]]+";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(urlTail);

			if (matcher.find()) {
				url = url.substring(0, lastSlash + 1);
			}
		}

		return url;
	}

	public static String removeParameters(String url) {
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	}

	public static boolean containsHashtag(String url) {
		if (url.contains("#")) {
			return true;
		}
		return false;
	}

	public static String removeNonWordCharacters(String word) {
		word = word.trim();
		word = word.replaceAll("[^\\p{L}]", "");
		// word = word.replaceAll("(^[^\\p{L}]|[^\\p{L}]$)", "");
		return word;
	}

	public static String removeQueryParameter(String url, String parameterName) {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			List<NameValuePair> queryParameters = uriBuilder.getQueryParams();
			for (Iterator<NameValuePair> queryParameterItr = queryParameters.iterator(); queryParameterItr.hasNext();) {
				NameValuePair queryParameter = queryParameterItr.next();
				if (queryParameter.getName().equals(parameterName)) {
					queryParameterItr.remove();
				}
			}
			uriBuilder.setParameters(queryParameters);
			return uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			LOGGER.error("Encountered an error while removing a query parameter from URL: " + e);
		}
		return null;
	}

}
