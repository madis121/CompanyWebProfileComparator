package ee.tlu.cwpc.dao;

import java.util.List;

import ee.tlu.cwpc.model.Profile;

public interface ProfileDAO {

	Profile findOne(long id);
	
	List<Profile> findAll();
	
	void save(Profile profile);
	
	void update(Profile profile);
	
	void delete(long id);
	
}
