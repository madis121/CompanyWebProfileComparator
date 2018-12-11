package ee.tlu.cwpc.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLHelper {
	
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
	
	public static boolean isMailto(String url) {
		if (url.toLowerCase().contains("mailto")) {
			return true;
		}
		
		return false;
	}

}
