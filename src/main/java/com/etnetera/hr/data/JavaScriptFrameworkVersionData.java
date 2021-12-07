package com.etnetera.hr.data;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Data entity describing version data of related JS framework.
 * 
 * @author jbambas
 *
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"java_script_framework_id", "version"}))
public class JavaScriptFrameworkVersionData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String version;
	
	@Column(nullable = true)
	private Timestamp deprecationDate;
	
	@Column(nullable = false, columnDefinition = "integer default 0")
	private int hypeLevel = 0;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private JavaScriptFramework javaScriptFramework;
	
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

	public Timestamp getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(Timestamp deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public int getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(int hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	public JavaScriptFramework getJavaScriptFramework() {
		return javaScriptFramework;
	}

	public void setJavaScriptFramework(JavaScriptFramework javaScriptFramework) {
		this.javaScriptFramework = javaScriptFramework;
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
		JavaScriptFrameworkVersionData other = (JavaScriptFrameworkVersionData) obj;
		return Objects.equals(deprecationDate, other.deprecationDate) && hypeLevel == other.hypeLevel
				&& Objects.equals(id, other.id) && Objects.equals(version, other.version);
	}
	
}
