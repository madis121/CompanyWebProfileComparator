package ee.tlu.cwpc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ee.tlu.cwpc.model.SearchResult;
import ee.tlu.cwpc.service.SearchResultService;

@Controller
@RequestMapping("search-results")
public class SearchResultsController extends BaseController {

	@Autowired
	private SearchResultService searchResultService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String openSearchResults(Model model) {
		List<SearchResult> searchResults = searchResultService.getSearchResults();
		model.addAttribute("searchResults", searchResults);
		addPageAttributesToModel(model);
		return "searchResults";
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String openSearchResultDetails(@RequestParam(name = "id") long id, Model model) {
		SearchResult searchResult = searchResultService.getSearchResult(id);
		model.addAttribute("searchResult", searchResult);
		addPageAttributesToModel(model);
		return "searchResultDetails";
	}
	
	@RequestMapping(value = "/delete-alert", method = RequestMethod.GET)
	public String deleteSearchResultAlert(@RequestParam(name = "id") long id, Model model) {
		SearchResult searchResult = searchResultService.getSearchResult(id);
		model.addAttribute("searchResult", searchResult);
		addPageAttributesToModel(model);
		return "deleteSearchResultAlert";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteSearchResult(@RequestParam(name = "id") long id) {
		searchResultService.deleteSearchResult(id);
		return "redirect:/search-results";
	}
	
}
