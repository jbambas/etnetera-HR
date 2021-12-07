package com.etnetera.hr.service;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.etnetera.hr.data.JavaScriptFramework;

/**
 * Interface for business logic service.
 * 
 * @author jbambas
 *
 */
public interface IJavaScriptFrameworkService {

	/**
	 * Access to stored JS frameworks and it's version data. 
	 * @param name optional search filter parameter for attribute name, if null then returns all frameworks
	 * @return collection of {@link JavaScriptFramework} entities accepted by given search filter parameters
	 */
	@NotNull
	public Collection<JavaScriptFramework> getFrameworks(String name);

	/**
	 * Method to save new {@link JavaScriptFramework}
	 * @param framework entity to be saved
	 * @return saved entity with assigned internal ids
	 * @throws NullPointerException if parameter framework is null
	 */
	@NotNull
	public JavaScriptFramework saveFramework(@NotNull JavaScriptFramework framework);

	/**
	 * Method to update existing {@link JavaScriptFramework}
	 * @param framework entity to be updated
	 * @return updated framework data
	 * @throws NullPointerException if parameter framework is null
	 */
	@NotNull
	public JavaScriptFramework updateFramework(@NotNull JavaScriptFramework framework);

	/**
	 * Method to delete framework and it's version data
	 * @param id identifier of deleting framework
	 */
	public void deleteFramework(long id);
	
}
