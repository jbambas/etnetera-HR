package com.etnetera.hr.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersionData;
import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.dto.JavaScriptFrameworkDto.FrameworkVersionDataDto;

public class JavaScriptFrameworkDtoDoMapperTest {

	private static final Long      DEFAULT_ID = Long.valueOf(1);
	private static final String    DEFAULT_VERSION = "1.0.0-b01";
	private static final String    DEFAULT_NAME = "Angular";
	private static final int       DEFAULT_HYPE_LEVEL = 1;
	private static final Timestamp DEFAULT_DEPRECATION_DATE = Timestamp.valueOf(LocalDate.ofEpochDay(0).atStartOfDay());
	private static final LocalDate DEFAULT_DEPRECATION_DATE_LOCAL_DATE = LocalDate.ofEpochDay(0);
	
	private JavaScriptFrameworkDtoDoMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new JavaScriptFrameworkDtoDoMapper();
	}
	
	@Test
	public void testFromDto() {
		JavaScriptFrameworkDto dto = new JavaScriptFrameworkDto();
		FrameworkVersionDataDto versionDto = new FrameworkVersionDataDto();
		versionDto.setDeprecationDate(DEFAULT_DEPRECATION_DATE_LOCAL_DATE);
		versionDto.setHypeLevel(DEFAULT_HYPE_LEVEL);
		versionDto.setId(DEFAULT_ID);
		versionDto.setVersion(DEFAULT_VERSION);
		dto.setFrameworkVersionData(Arrays.asList(versionDto));
		dto.setId(DEFAULT_ID);
		dto.setName(DEFAULT_NAME);

		JavaScriptFramework result = mapper.mapFromDto(dto);
		assertNotNull(result);
		assertEquals(DEFAULT_ID, result.getId());
		assertEquals(DEFAULT_NAME, result.getName());
		assertNotNull(result.getFrameworkVersionData());
		assertEquals(1, result.getFrameworkVersionData().size());
		assertEquals(DEFAULT_DEPRECATION_DATE, result.getFrameworkVersionData().get(0).getDeprecationDate());
		assertEquals(DEFAULT_HYPE_LEVEL, result.getFrameworkVersionData().get(0).getHypeLevel());
		assertEquals(DEFAULT_ID, result.getFrameworkVersionData().get(0).getId());
		assertEquals(DEFAULT_VERSION, result.getFrameworkVersionData().get(0).getVersion());
	}
	
	@Test
	public void testToDto() {
		JavaScriptFramework javaScriptFramework = new JavaScriptFramework();
		javaScriptFramework.setId(DEFAULT_ID);
		javaScriptFramework.setName(DEFAULT_NAME);
		JavaScriptFrameworkVersionData version = new JavaScriptFrameworkVersionData();
		version.setDeprecationDate(DEFAULT_DEPRECATION_DATE);
		version.setHypeLevel(DEFAULT_HYPE_LEVEL);
		version.setId(DEFAULT_ID);
		version.setVersion(DEFAULT_VERSION);
		javaScriptFramework.setFrameworkVersionData(Arrays.asList(version));

		JavaScriptFrameworkDto result = mapper.mapToDto(javaScriptFramework);
		assertNotNull(result);
		assertEquals(DEFAULT_ID, result.getId());
		assertEquals(DEFAULT_NAME, result.getName());
		assertNotNull(result.getFrameworkVersionData());
		assertEquals(1, result.getFrameworkVersionData().size());
		assertEquals(DEFAULT_DEPRECATION_DATE_LOCAL_DATE, result.getFrameworkVersionData().get(0).getDeprecationDate());
		assertEquals(DEFAULT_HYPE_LEVEL, result.getFrameworkVersionData().get(0).getHypeLevel());
		assertEquals(DEFAULT_ID, result.getFrameworkVersionData().get(0).getId());
		assertEquals(DEFAULT_VERSION, result.getFrameworkVersionData().get(0).getVersion());
	}
	
	@Test
	public void testNullCheck() {
		assertNull(mapper.mapFromDto(null));
		assertNull(mapper.mapToDto(null));
	}
}
