package ee.tlu.cwpc.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ee.tlu.cwpc.dao.SearchResultDAO;
import ee.tlu.cwpc.model.SearchResult;

@Repository("searchResultDAO")
public class BasicSearchResultDAO implements SearchResultDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public SearchResult findOne(long id) {
		Session session = sessionFactory.getCurrentSession();
		SearchResult searchResult = session.get(SearchResult.class, id);
		return searchResult;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SearchResult> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query<SearchResult> query = session.createQuery("FROM SearchResult ORDER BY id");
		List<SearchResult> searchResults = query.list();
		return searchResults;
	}

	@Override
	public void save(SearchResult searchResult) {
		Session session = sessionFactory.getCurrentSession();
		session.save(searchResult);
	}

	@Override
	public void update(SearchResult searchResult) {
		Session session = sessionFactory.getCurrentSession();
		session.update(searchResult);
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		SearchResult searchResult = findOne(id);
		session.delete(searchResult);
	}

}
