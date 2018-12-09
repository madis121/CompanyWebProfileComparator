package ee.tlu.cwpc.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ee.tlu.cwpc.dao.SettingsDAO;
import ee.tlu.cwpc.model.Settings;

@Repository("settingsDAO")
public class BasicSettingsDAO implements SettingsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Settings findOne(long id) {
		Session session = sessionFactory.getCurrentSession();
		Settings settings = session.get(Settings.class, id);
		return settings;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Settings> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query<Settings> query = session.createQuery("FROM Settings ORDER BY id");
		List<Settings> settings = query.list();
		return settings;
	}

	@Override
	public void save(Settings settings) {
		Session session = sessionFactory.getCurrentSession();
		session.save(settings);
	}

	@Override
	public void update(Settings settings) {
		Session session = sessionFactory.getCurrentSession();
		session.update(settings);
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Settings settings = findOne(id);
		session.delete(settings);
	}

}
