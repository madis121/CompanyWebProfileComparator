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
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String url;

	private DateTime visited;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;

	public Url() {

	}

	public Url(String url) {
		this.url = url;
		this.visited = DateTime.now();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DateTime getVisited() {
		return visited;
	}

	public void setVisited(DateTime visited) {
		this.visited = visited;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Url [id=" + id + ", url=" + url + ", visited=" + visited + "]";
	}

}
