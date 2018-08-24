package ee.tlu.cwpc.service;

import java.util.List;

import ee.tlu.cwpc.model.Contact;
import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.SearchResult;
import ee.tlu.cwpc.model.Url;

public interface SearchResultService {

	SearchResult getSearchResult(long id);
	
	List<SearchResult> getSearchResults();
	
	void createSearchResult(String name, String countryCode, List<Url> urls, List<Keyword> keywords, List<Contact> contacts);
	
	void deleteSearchResult(long id);
	
}
