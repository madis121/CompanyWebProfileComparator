package ee.tlu.cwpc.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import ee.tlu.cwpc.dao.ProfileDAO;
import ee.tlu.cwpc.model.Profile;

@Repository("profileDAO")
public class BasicProfileDAO implements ProfileDAO {

  @Autowired
  private HibernateTemplate hibernateTemplate;

  @Override
  public Profile findOne(long id) {
    Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
    Profile profile = session.get(Profile.class, id);
    return profile;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Profile> findAll() {
    Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
    Query<Profile> query = session.createQuery("FROM Profile ORDER BY id");
    List<Profile> profiles = query.list();
    return profiles;
  }

  @Override
  public void save(Profile profile) {
    Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
    session.save(profile);
  }

  @Override
  public void update(Profile profile) {
    Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
    session.update(profile);
  }

  @Override
  public void delete(long id) {
    Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
    Profile profile = findOne(id);
    session.delete(profile);
  }

}
