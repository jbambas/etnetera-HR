package com.etnetera.hr.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.collections4.CollectionUtils;

import com.etnetera.hr.data.JavaScriptFramework;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Data transfer object for {@link JavaScriptFramework}.
 * 
 * @author jbambas
 *
 */
public class JavaScriptFrameworkDto {

	private Long id;
	@NotEmpty(message = "Framework name is mandatory.")
	private String name;
	private List<FrameworkVersionDataDto> frameworkVersionData;
	

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
	

	public List<FrameworkVersionDataDto> getFrameworkVersionData() {
		return frameworkVersionData;
	}


	public void setFrameworkVersionData(List<FrameworkVersionDataDto> frameworkVersionData) {
		this.frameworkVersionData = frameworkVersionData;
	}
	
	@Override
	public String toString() {
		return "JavaScriptFrameworkDto (id=" + id + ", name=" + name + ", frameworkVersionData=" + frameworkVersionData + ")";
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
		JavaScriptFrameworkDto other = (JavaScriptFrameworkDto) obj;
		return CollectionUtils.isEqualCollection(frameworkVersionData, other.frameworkVersionData) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}


	public static class FrameworkVersionDataDto {
		Long id;
		@NotEmpty(message = "Framework version is mandatory.")
		String version;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	    LocalDate deprecationDate;
		int hypeLevel=0;

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public LocalDate getDeprecationDate() {
			return deprecationDate;
		}
		public void setDeprecationDate(LocalDate deprecationDate) {
			this.deprecationDate = deprecationDate;
		}
		public int getHypeLevel() {
			return hypeLevel;
		}
		public void setHypeLevel(int hypeLevel) {
			this.hypeLevel = hypeLevel;
		}
		@Override
		public String toString() {
			return "VersionData (id=" + id + ", version=" + version + ", deprecationDate=" + deprecationDate + ", hypeLevel=" + hypeLevel +")";
		}
		@Override
		public int hashCode() {
			return Objects.hash(deprecationDate, hypeLevel, id, version);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FrameworkVersionDataDto other = (FrameworkVersionDataDto) obj;
			return Objects.equals(deprecationDate, other.deprecationDate) && hypeLevel == other.hypeLevel
					&& Objects.equals(id, other.id) && Objects.equals(version, other.version);
		}
	}
	
}
