package com.govtech.restaurantpicker.model.vo;

public class ErrorResponseVO {

	private String error;
	private String description;

	public ErrorResponseVO(String error, String description) {
		this.error = error;
		this.description = description;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
