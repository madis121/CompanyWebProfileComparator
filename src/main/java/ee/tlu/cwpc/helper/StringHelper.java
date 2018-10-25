package ee.tlu.cwpc.helper;

public class StringHelper {

	public static String removeNonWordCharacters(String word) {
		word = word.trim();
		word = word.replaceAll("[^\\p{L}]", "");
		// word = word.replaceAll("(^[^\\p{L}]|[^\\p{L}]$)", "");
		return word;
	}
	
}
