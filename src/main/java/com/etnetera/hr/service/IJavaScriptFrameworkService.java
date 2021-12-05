package com.etnetera.hr.service;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.etnetera.hr.data.JavaScriptFramework;

public interface IJavaScriptFrameworkService {

	@NotNull
	public Collection<JavaScriptFramework> getAllFrameworks();
}
