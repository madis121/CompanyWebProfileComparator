package ee.tlu.cwpc.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.tlu.cwpc.dao.ProfileDAO;
import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.Profile;
import ee.tlu.cwpc.model.Url;
import ee.tlu.cwpc.service.ProfileService;

@Service("profileService")
public class BasicProfileService implements ProfileService {
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
		Profile profile = new Profile();
		profile.setName(name);
		
		for (Url url : urls) {
			profile.addUrl(url);
		}
		for (Keyword keyword : keywords) {
			profile.addKeyword(keyword);
		}
		
		DateTime date = DateTime.now();
		profile.setCreated(date);
		profile.setUpdated(date);
		profileDAO.save(profile);
	}

	@Override
	@Transactional
	public void updateProfile(long id, String name, List<Keyword> keywords) {
		Profile profile = profileDAO.findOne(id);
		profile.setName(name);
		profile.getKeywords().clear();
		
		for (Keyword keyword : keywords) {
			profile.addKeyword(keyword);
		}
		
		profile.setUpdated(DateTime.now());
		profileDAO.update(profile);
	}

	@Override
	@Transactional
	public void deleteProfile(long id) {
		profileDAO.delete(id);
	}

	@Override
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> getProfileKeywords(long id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT word FROM cwpc.keyword WHERE profile_id = :profileId ORDER BY id ASC";
		NativeQuery query = session.createSQLQuery(sql);
		query.setParameter("profileId", id);
		List<String> keywords = query.list();
		return keywords;
	}

}
