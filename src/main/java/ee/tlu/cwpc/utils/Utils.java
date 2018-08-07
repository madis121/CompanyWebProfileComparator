package ee.tlu.cwpc.utils;

public class Utils {

	public static boolean urlContainsHashtag(String url) {
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}

		if (url.contains("#")) {
			return true;
		}
		return false;
	}
	
	public static String removeNonWordCharacters(String word) {
		word = word.replaceAll("[^\\p{L}]", "");
		return word;
	}
	
}
