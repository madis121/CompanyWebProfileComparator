package ee.tlu.cwpc.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public class CSEObject {

	private HttpStatus statusCode;

	private String statusText;

	private List<String> links;

	public CSEObject(HttpStatus statusCode, String statusText) {
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public CSEObject(HttpStatus statusCode, List<String> links) {
		this.statusCode = statusCode;
		this.links = links;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

}
