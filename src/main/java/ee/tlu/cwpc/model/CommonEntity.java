package ee.tlu.cwpc.model;

import org.joda.time.DateTime;

public interface CommonEntity {

  long getId();

  void setId(long id);

  void setCreated(DateTime created);

  void setUpdated(DateTime updated);

}
