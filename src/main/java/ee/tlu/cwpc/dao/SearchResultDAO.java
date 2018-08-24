package ee.tlu.cwpc.dao;

import java.util.List;

import ee.tlu.cwpc.model.SearchResult;

public interface SearchResultDAO {

	SearchResult findOne(long id);

	List<SearchResult> findAll();

	void save(SearchResult searchResult);

	void update(SearchResult searchResult);

	void delete(long id);

}
