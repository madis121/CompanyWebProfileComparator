package ee.tlu.cwpc.service;

import java.util.List;

import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.model.Url;

public interface ProfileService {

	Profile getProfile(long id);
	
	List<Profile> getProfiles();
	
	void createProfile(String name, List<Url> urls, List<Keyword> keywords);
	
	void updateProfile(long id, String name, List<Keyword> keywords);
	
	void deleteProfile(long id);
	
	List<String> getProfileKeywords(long id);
	
}
