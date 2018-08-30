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
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;

	private String lastName;

	private String field;

	private String phone;

	private String linkedIn;

	@ManyToOne
	@JoinColumn(name = "company_profile_id")
	private CompanyProfile companyProfile;

	private DateTime created;

	private DateTime updated;

	public Subject() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
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
