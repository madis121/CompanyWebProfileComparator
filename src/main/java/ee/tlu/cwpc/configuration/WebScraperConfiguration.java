package ee.tlu.cwpc.configuration;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebScraperConfiguration {

	@Value("${webscraper.max.pages.to.search:#{null}}")
	private Integer maxPagesToSearch;

	@Value("${webscraper.ignore.words.with.length:#{0}}")
	private Integer ignoreWordsWithLength;

	@Value("#{'${webscraper.ignored.html.elements}'.split(',')}")
	private Set<String> ignoredHtmlElements;

	@Value("#{'${webscraper.redundant.words}'.split(',')}")
	private Set<String> redundantWords;

	public Integer getMaxPagesToSearch() {
		return maxPagesToSearch;
	}

	public void setMaxPagesToSearch(Integer maxPagesToSearch) {
		this.maxPagesToSearch = maxPagesToSearch;
	}

	public Set<String> getIgnoredHtmlElements() {
		return ignoredHtmlElements;
	}

	public Integer getIgnoreWordsWithLength() {
		return ignoreWordsWithLength;
	}

	public void setIgnoreWordsWithLength(Integer ignoreWordsWithLength) {
		this.ignoreWordsWithLength = ignoreWordsWithLength;
	}

	public void setIgnoredHtmlElements(Set<String> ignoredHtmlElements) {
		this.ignoredHtmlElements = ignoredHtmlElements;
	}

	public Set<String> getRedundantWords() {
		return redundantWords;
	}

	public void setRedundantWords(Set<String> redundantWords) {
		this.redundantWords = redundantWords;
	}

}
