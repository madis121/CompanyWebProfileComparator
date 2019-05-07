package ee.tlu.cwpc.service;

import java.util.List;
import ee.tlu.cwpc.dto.ProfileDTO;

public interface ProfileService {

  ProfileDTO getProfile(long id);

  List<ProfileDTO> getProfiles();

  void createProfile(String name, List<String> urls, List<String> keywords);

  void updateProfile(long id, String name, List<String> keywords);

  void deleteProfile(long id);

  List<String> getKeywordsByProfileId(long profileId);

  List<String> getUrlsByProfileId(long id);

}
