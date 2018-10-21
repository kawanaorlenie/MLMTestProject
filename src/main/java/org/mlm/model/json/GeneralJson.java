package org.mlm.model.json;

import org.codehaus.jackson.annotate.JsonProperty;

public class GeneralJson {

	@JsonProperty(value = "Result")
	private String result;
	@JsonProperty(value = "Records")
	private Object records;
	@JsonProperty(value = "Message")
	private String message;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getRecords() {
		return records;
	}

	public void setRecords(Object records) {
		this.records = records;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GeneralJson(String result, Object records, String message) {
		this.result = result;
		this.records = records;
		this.message = message;
	}

}
