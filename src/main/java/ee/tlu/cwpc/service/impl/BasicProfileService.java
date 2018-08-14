package ee.tlu.cwpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.tlu.cwpc.dao.ProfileDAO;
import ee.tlu.cwpc.helper.DAOHelper;
import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.model.Url;
import ee.tlu.cwpc.service.ProfileService;

@Service("profileService")
public class BasicProfileService implements ProfileService {
	
	@Autowired
	private ProfileDAO profileDAO;
	
	@Override
	@Transactional
	public Profile getProfile(long id) {
		Profile profile = profileDAO.findOne(id);
		return profile;
	}

	@Override
	@Transactional
	public List<Profile> getProfiles() {
		List<Profile> profiles = profileDAO.findAll();
		return profiles;
	}

	@Override
	@Transactional
	public void createProfile(String name, List<Url> urls, List<Keyword> keywords) {
		Profile profile = DAOHelper.createProfile(name, urls, keywords);
		profileDAO.save(profile);
	}

	@Override
	@Transactional
	public void updateProfile(Profile profile) {
		profileDAO.update(profile);
	}

	@Override
	@Transactional
	public void deleteProfile(long id) {
		profileDAO.delete(id);
	}

}
