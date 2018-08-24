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
	public void createProfile(String name, List<String> urls, List<String> keywords) {
		DateTime date = DateTime.now();
		Profile profile = new Profile();
		profile.setName(name);
		
		for (String urlString : urls) {
			Url url = new Url(urlString, date, date);
			profile.addUrl(url);
		}
		
		for (String keywordString : keywords) {
			Keyword keyword = new Keyword(keywordString, date, date);
			profile.addKeyword(keyword);
		}
		
		profile.setCreated(date);
		profile.setUpdated(date);
		profileDAO.save(profile);
	}

	@Override
	@Transactional
	public void updateProfile(long id, String name, List<String> keywords) {
		DateTime date = DateTime.now();
		Profile profile = profileDAO.findOne(id);
		profile.setName(name);
		profile.getKeywords().clear();
		
		for (String keywordString : keywords) {
			Keyword keyword = new Keyword(keywordString, date, date);
			profile.addKeyword(keyword);
		}
		
		profile.setUpdated(date);
		profileDAO.update(profile);
	}

	@Override
	@Transactional
	public void deleteProfile(long id) {
		profileDAO.delete(id);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getProfileKeywords(long id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT keyword FROM cwpc.keyword WHERE profile_id = :profileId ORDER BY id ASC";
		NativeQuery<String> query = session.createSQLQuery(sql);
		query.setParameter("profileId", id);
		List<String> keywords = query.list();
		return keywords;
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getProfileURLs(long id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT url FROM cwpc.url WHERE profile_id = :profileId ORDER BY id ASC";
		NativeQuery<String> query = session.createSQLQuery(sql);
		query.setParameter("profileId", id);
		List<String> urls = query.list();
		return urls;
	}

}
