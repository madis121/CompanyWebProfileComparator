package ee.tlu.cwpc.helper;

import java.util.List;
import java.util.stream.Collectors;

public class CSEHelper {

	public static String constructQuery(List<String> keywords, List<String> usedUrls) {
		String query = keywords.stream().collect(Collectors.joining(" OR ", "", ""));
		
		if (!usedUrls.isEmpty()) {
			query += " ".concat(usedUrls.stream().collect(Collectors.joining(" ", "-site:", "")));
		}
		
		return query;
	}

}
