package ee.tlu.cwpc.service;

import java.util.List;

import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.model.Url;

public interface ProfileService {

	Profile getProfile(long id);
	
	List<Profile> getProfiles();
	
	void createProfile(String name, List<Url> urls, List<Keyword> keywords);
	
	void updateProfile(Profile profile);
	
	void deleteProfile(long id);
	
}
