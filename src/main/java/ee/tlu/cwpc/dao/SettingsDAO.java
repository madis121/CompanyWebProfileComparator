package ee.tlu.cwpc.dao;

import java.util.List;

import ee.tlu.cwpc.model.Settings;

public interface SettingsDAO {

	Settings findOne(long id);
	
	List<Settings> findAll();
	
	void save(Settings settings);
	
	void update(Settings settings);
	
	void delete(long id);
	
}
