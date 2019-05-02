package ee.tlu.cwpc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table
public class Keyword implements CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String keyword;

	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@ManyToOne
	@JoinColumn(name = "search_result_id")
	private SearchResult searchResult;

	private DateTime created;

	private DateTime updated;

	public Keyword() {

	}

	public Keyword(String keyword, DateTime created, DateTime updated) {
		this.keyword = keyword;
		this.created = created;
		this.updated = updated;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public SearchResult getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Keyword [id=" + id + ", word=" + keyword + ", created=" + created + ", updated=" + updated + "]";
	}

}
