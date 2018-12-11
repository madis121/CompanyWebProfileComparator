package ee.tlu.cwpc.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringHelper.class);

	public static String removeNonWordCharacters(String word) {
		word = word.trim();
		word = word.replaceAll("[^\\p{L}]", "");
		// word = word.replaceAll("(^[^\\p{L}]|[^\\p{L}]$)", "");
		return word;
	}
	
	public static Set<String> splitStringToSet(String string, String separator) {
		if (string == null) {
			return new HashSet<String>();
		}
		
		Set<String> items = new HashSet<String>(Arrays.asList(string.split(",")));
		return items;
	}

	public static double compareStringSets(Set<String> setA, Set<String> setB) {
		double distanceSum = 0;
		double distanceNum = 0;

		for (String left : setA) {
			LOGGER.trace(String.format("Comparing word %s with: ", left));
			double closestDistance = 0;
			String closestString = "";

			for (String right : setB) {
				double jaroWinklerDistance = getJaroWinklerDistance(left, right);
				LOGGER.trace(String.format("	%s - distance: %f", right, jaroWinklerDistance));

				if (closestDistance < jaroWinklerDistance) {
					closestDistance = jaroWinklerDistance;
					closestString = right;
				}
			}

			LOGGER.trace(String.format("		Closest distance: %f (%s)", closestDistance, closestString));
			distanceSum += closestDistance;
			distanceNum++;
		}

		return distanceSum / distanceNum;
	}

	public static double getJaroWinklerDistance(String left, String right) {
		JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
		return jaroWinklerDistance.apply(left, right);
	}

}
