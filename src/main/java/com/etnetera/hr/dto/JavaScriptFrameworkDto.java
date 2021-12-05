package com.etnetera.hr.dto;

import javax.validation.constraints.NotEmpty;

public class JavaScriptFrameworkDto {

	private Long id;
	@NotEmpty(message = "Framework name is mandatory.")
	private String name;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
