package ee.tlu.cwpc.dao.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ee.tlu.cwpc.dao.Crud;
import ee.tlu.cwpc.model.CommonEntity;

@Service(value = "crud")
public class SimpleCrud implements Crud {

  @Autowired
  protected HibernateTemplate hibernate;

  @SuppressWarnings("unchecked")
  @Override
  public <T extends CommonEntity> T get(long id, Class<?> clazz) {
    return (T) hibernate.get(clazz, id);
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public <T> List<T> find(DetachedCriteria criteria) {
    return (List<T>) hibernate.findByCriteria(criteria);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> List<T> findWithLimit(DetachedCriteria criteria, int start, int offset) {
    return (List<T>) hibernate.findByCriteria(criteria, start, offset);
  }

  @Override
  public void save(CommonEntity model) {
    model = setCommonEntityProperties(model);
    hibernate.saveOrUpdate(model);
  }

  @Override
  public void delete(Long id, Class<?> clazz) {
    hibernate.delete(hibernate.load(clazz, id));
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> List<T> nativeSQLList(String sql, Class<?> clazz) {
    return (List<T>) hibernate.getSessionFactory().getCurrentSession().createNativeQuery(sql, clazz)
        .getResultList();
  }

  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW,
      rollbackFor = Exception.class)
  @Override
  public Object executeSQL(String sql) {
    return hibernate.getSessionFactory().getCurrentSession().createNativeQuery(sql)
        .getSingleResult();
  }

  private CommonEntity setCommonEntityProperties(CommonEntity model) {
    if (isNew(model)) {
      model.setCreated(new DateTime());
    } else {
      model.setUpdated(new DateTime());
    }
    return model;
  }

  private boolean isNew(CommonEntity model) {
    return model.getId() == 0;
  }

}
