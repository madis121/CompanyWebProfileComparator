package ee.tlu.cwpc.helper;

import java.util.List;

import org.joda.time.DateTime;

import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.model.Url;

public class DAOHelper {

	public static Profile createProfile(String name, List<Url> urls, List<Keyword> keywords) {
		Profile profile = new Profile();
		profile.setName(name);
		
		for (Url url : urls) {
			profile.addUrl(url);
		}
		
		for (Keyword keyword : keywords) {
			profile.addKeyword(keyword);
		}
		
		profile.setCreated(DateTime.now());
		return profile;
	}

}
