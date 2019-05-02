package ee.tlu.cwpc.dao;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import ee.tlu.cwpc.model.CommonEntity;

public interface Crud {

  <T extends CommonEntity> T get(long id, Class<?> clazz);

  <T> List<T> find(DetachedCriteria criteria);

  <T> List<T> findWithLimit(DetachedCriteria criteria, int start, int offset);

  void save(CommonEntity model);

  void delete(Long id, Class<?> clazz);

  <T> List<T> nativeSQLList(String sql, Class<?> clazz);

  Object executeSQL(String sql);

}
