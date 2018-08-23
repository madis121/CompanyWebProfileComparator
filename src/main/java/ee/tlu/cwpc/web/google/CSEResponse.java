package ee.tlu.cwpc.web.google;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CSEResponse {

	@JsonProperty("items")
	private List<CSEItem> items = new ArrayList<>();

	public List<CSEItem> getItems() {
		return items;
	}

	public void setItems(List<CSEItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "CSEResponse [items=" + items + "]";
	}

}
