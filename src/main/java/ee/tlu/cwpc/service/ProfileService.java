package ee.tlu.cwpc.service;

import java.util.List;
import ee.tlu.cwpc.dto.ProfileDTO;
import ee.tlu.cwpc.model.Profile;

public interface ProfileService {

  Profile getProfile(long id);

  List<Profile> getProfiles();
  
  List<ProfileDTO> getProfiles2();

  void createProfile(String name, List<String> urls, List<String> keywords);

  void updateProfile(long id, String name, List<String> keywords);

  void deleteProfile(long id);

  List<String> getProfileKeywords(long id);

  List<String> getProfileURLs(long id);

}
