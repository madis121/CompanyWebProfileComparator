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
public class Keyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String word;

	private DateTime added;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;

	public Keyword() {
		
	}

	public Keyword(String word) {
		this.word = word;
		this.added = DateTime.now();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public DateTime getAdded() {
		return added;
	}

	public void setAdded(DateTime added) {
		this.added = added;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Keyword [id=" + id + ", word=" + word + ", added=" + added + "]";
	}

}
