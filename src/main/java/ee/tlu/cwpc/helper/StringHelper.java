package ee.tlu.cwpc.helper;

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

	public static double compareStringSets(Set<String> setA, Set<String> setB) {
		double distanceSum = 0;
		double distanceNum = 0;

		for (String keywordA : setA) {
			LOGGER.trace(String.format("Comparing word %s with: ", keywordA));
			double closestDistance = 0;

			for (String keywordB : setB) {
				double jaroWinklerDistance = getJaroWinklerDistance(keywordA, keywordB);
				LOGGER.trace(String.format("	%s, distance: %f", keywordB, jaroWinklerDistance));

				if (closestDistance < jaroWinklerDistance) {
					closestDistance = jaroWinklerDistance;
				}
			}

			LOGGER.trace(String.format("	*** Closest distance: %f ***", closestDistance));
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
