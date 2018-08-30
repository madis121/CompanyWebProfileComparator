package ee.tlu.cwpc.service;

import java.util.List;

import ee.tlu.cwpc.model.CompanyProfile;
import ee.tlu.cwpc.model.SearchResult;

public interface SearchResultService {

	SearchResult getSearchResult(long id);

	List<SearchResult> getSearchResults();

	void createSearchResult(String name, String countryCode, List<String> urls, List<String> keywords,
			List<String> contacts, List<CompanyProfile> companyProfiles);

	void deleteSearchResult(long id);

}
