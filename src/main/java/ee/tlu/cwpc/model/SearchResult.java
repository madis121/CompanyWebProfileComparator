package ee.tlu.cwpc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;

@Entity
@Table
public class SearchResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String countryCode;

	@OneToMany(mappedBy = "searchResult", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Url> urls = new ArrayList<>();

	@OneToMany(mappedBy = "searchResult", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Keyword> keywords = new ArrayList<>();

	@OneToMany(mappedBy = "searchResult", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Contact> contacts = new ArrayList<>();

	private DateTime created;

	private DateTime updated;

	public SearchResult() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

	public void addUrl(Url url) {
		urls.add(url);
		url.setSearchResult(this);
	}

	public void removeUrl(Url url) {
		urls.remove(url);
		url.setSearchResult(null);
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public void addKeyword(Keyword keyword) {
		keywords.add(keyword);
		keyword.setSearchResult(this);
	}

	public void removeKeyword(Keyword keyword) {
		keywords.remove(keyword);
		keyword.setSearchResult(null);
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public void addContact(Contact contact) {
		contacts.add(contact);
		contact.setSearchResult(this);
	}

	public void removeContact(Contact contact) {
		contacts.remove(contact);
		contact.setSearchResult(null);
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

}
