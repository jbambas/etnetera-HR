package com.etnetera.hr.data;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "javaScriptFramework", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<JavaScriptFrameworkVersionData> frameworkVersionData;

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
	
	public List<JavaScriptFrameworkVersionData> getFrameworkVersionData() {
		return frameworkVersionData;
	}

	public void setFrameworkVersionData(List<JavaScriptFrameworkVersionData> frameworkVersionData) {
		this.frameworkVersionData = frameworkVersionData;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework (id=" + id + ", name=" + name + ", frameworkVersionData=" + frameworkVersionData + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(frameworkVersionData, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaScriptFramework other = (JavaScriptFramework) obj;

		return CollectionUtils.isEqualCollection(frameworkVersionData, other.frameworkVersionData) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}
