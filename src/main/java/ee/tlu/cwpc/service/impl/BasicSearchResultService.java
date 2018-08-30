package ee.tlu.cwpc.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.tlu.cwpc.dao.SearchResultDAO;
import ee.tlu.cwpc.model.CompanyProfile;
import ee.tlu.cwpc.model.Contact;
import ee.tlu.cwpc.model.Keyword;
import ee.tlu.cwpc.model.SearchResult;
import ee.tlu.cwpc.model.Url;
import ee.tlu.cwpc.service.SearchResultService;

@Service("searchResultService")
public class BasicSearchResultService implements SearchResultService {

	@Autowired
	private SearchResultDAO searchResultDAO;

	@Override
	@Transactional
	public SearchResult getSearchResult(long id) {
		SearchResult searchResult = searchResultDAO.findOne(id);
		return searchResult;
	}

	@Override
	@Transactional
	public List<SearchResult> getSearchResults() {
		List<SearchResult> searchResults = searchResultDAO.findAll();
		return searchResults;
	}

	@Override
	@Transactional
	public void createSearchResult(String name, String countryCode, List<String> urls, List<String> keywords,
			List<String> contacts, List<CompanyProfile> companyProfiles) {
		DateTime date = DateTime.now();
		SearchResult searchResult = new SearchResult();
		searchResult.setName(name);
		searchResult.setCountryCode(countryCode);

		for (String urlString : urls) {
			Url url = new Url(urlString, date, date);
			searchResult.addUrl(url);
		}

		for (String keywordString : keywords) {
			Keyword keyword = new Keyword(keywordString, date, date);
			searchResult.addKeyword(keyword);
		}

		for (String contactString : contacts) {
			Contact contact = new Contact(contactString, date, date);
			searchResult.addContact(contact);
		}

		for (CompanyProfile companyProfile : companyProfiles) {
			searchResult.addCompanyProfile(companyProfile);
		}

		searchResult.setCreated(date);
		searchResult.setUpdated(date);
		searchResultDAO.save(searchResult);
	}

	@Override
	@Transactional
	public void deleteSearchResult(long id) {
		searchResultDAO.delete(id);
	}

}
