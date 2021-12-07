package com.etnetera.hr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersionData;
import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.dto.JavaScriptFrameworkDto.FrameworkVersionDataDto;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import net.bytebuddy.NamingStrategy.SuffixingRandom.BaseNameResolver.ForGivenType;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class JavaScriptFrameworkTests {
	
	private static final String DEFAULT_URI = "/frameworks";
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	private JavaScriptFrameworkRepository repository;
	
	@Before
	public void setUp() {
	      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	      repository = webApplicationContext.getBean(JavaScriptFrameworkRepository.class);
	}
	
	@Test
	@DirtiesContext
	public void getFrameworksTest() throws Exception {
		//init some data
		JavaScriptFrameworkDto frameworkA = createFrameworkDto("React");
		JavaScriptFrameworkDto frameworkB = createFrameworkDto("NodeJS");
		repository.save(createFramework(frameworkA.getName()));
		repository.save(createFramework(frameworkB.getName()));

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URI).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		
		JavaScriptFrameworkDto[] result = mapFromJson(content, JavaScriptFrameworkDto[].class);
		List<JavaScriptFrameworkDto> resultList = Arrays.asList(result);
		assertEquals(2, resultList.size());
		// sort results to get always same order for assertion
		resultList.sort((x,y) -> Long.compare(x.getId(), y.getId()));
		// React assertion
		assertFrameworkDtoResponse(frameworkA, resultList.get(0));
		// NodeJS assertion
		assertFrameworkDtoResponse(frameworkB, resultList.get(1));
	}
	

	
	@Test
	@DirtiesContext
	public void getFrameworks_searchingTest() throws Exception {
		//init some data
		JavaScriptFrameworkDto frameworkA = createFrameworkDto("React");
		JavaScriptFrameworkDto frameworkB = createFrameworkDto("NodeJS");
		repository.save(createFramework(frameworkA.getName()));
		repository.save(createFramework(frameworkB.getName()));

		// search for React
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URI).param("name", frameworkA.getName()).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		JavaScriptFrameworkDto[] result = mapFromJson(content, JavaScriptFrameworkDto[].class);
		List<JavaScriptFrameworkDto> resultList = Arrays.asList(result);
		assertEquals(1, resultList.size());
		assertFrameworkDtoResponse(frameworkA, resultList.get(0));
		
		// search for NodeJs
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URI).param("name", frameworkB.getName()).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		content = mvcResult.getResponse().getContentAsString();
		result = mapFromJson(content, JavaScriptFrameworkDto[].class);
		resultList = Arrays.asList(result);
		assertEquals(1, resultList.size());
		assertFrameworkDtoResponse(frameworkB, resultList.get(0));
		
		// search for unknown framework
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URI).param("name", "dummy").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		content = mvcResult.getResponse().getContentAsString();
		result = mapFromJson(content, JavaScriptFrameworkDto[].class);
		resultList = Arrays.asList(result);
		assertEquals(0, resultList.size());
	}
	
	@Test
	@DirtiesContext
	public void insertFrameworkTest() throws Exception {
		JavaScriptFrameworkDto frameworkA = createFrameworkDto("React");
		// insert new framework
		String requestJson = mapToJson(frameworkA);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(DEFAULT_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		JavaScriptFrameworkDto result = mapFromJson(content, JavaScriptFrameworkDto.class);
		assertFrameworkDtoResponse(frameworkA, result);
		
		// now I will insert it again -> conflict happened because of unique name constraint, HTTP 409 Conflict expected
		mvcResult = mvc.perform(MockMvcRequestBuilders.post(DEFAULT_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.CONFLICT.value(), status);
	}
	
	@Test
	@DirtiesContext
	public void upsertFrameworkTest() throws Exception {
		JavaScriptFrameworkDto frameworkA = createFrameworkDto("React");
		// insert new framework by PUT method
		String requestJson = mapToJson(frameworkA);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(DEFAULT_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		String content = mvcResult.getResponse().getContentAsString();
		JavaScriptFrameworkDto result = mapFromJson(content, JavaScriptFrameworkDto.class);
		assertFrameworkDtoResponse(frameworkA, result);
		
		// now I will update name
		frameworkA = result;
		frameworkA.setName("React JS");
		requestJson = mapToJson(frameworkA);
		mvcResult = mvc.perform(MockMvcRequestBuilders.put(DEFAULT_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		content = mvcResult.getResponse().getContentAsString();
		result = mapFromJson(content, JavaScriptFrameworkDto.class);
		assertEquals(frameworkA, result);
		
		// now I will add new version
		FrameworkVersionDataDto newVersion = new FrameworkVersionDataDto();
		newVersion.setVersion("2.0.0-b01");
		frameworkA.getFrameworkVersionData().add(newVersion);
		requestJson = mapToJson(frameworkA);
		mvcResult = mvc.perform(MockMvcRequestBuilders.put(DEFAULT_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
		content = mvcResult.getResponse().getContentAsString();
		result = mapFromJson(content, JavaScriptFrameworkDto.class);
		assertEquals(2, result.getFrameworkVersionData().size());
		assertEquals(frameworkA.getFrameworkVersionData().get(0).getVersion(), result.getFrameworkVersionData().get(0).getVersion());
		assertEquals(frameworkA.getFrameworkVersionData().get(1).getVersion(), result.getFrameworkVersionData().get(1).getVersion());
	}
	
	@Test
	@DirtiesContext
	public void deleteFrameworkTest() throws Exception {
		//init some data
		JavaScriptFrameworkDto framework = createFrameworkDto("React");
		JavaScriptFramework savedFramework = repository.save(createFramework(framework.getName()));
		
		// invoke delete operation for unknown id
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URI + "/{id}", Long.MAX_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.NO_CONTENT.value(), status);
		// check that nothing happened with inserted entry
		Iterator<JavaScriptFramework> it = repository.findAll().iterator();
		assertTrue(it.hasNext());
		assertEquals(savedFramework, it.next());
		
		// now invoke delete operation for id of saved entity
		mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URI + "/{id}", savedFramework.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.NO_CONTENT.value(), status);
		// now check that entry is really deleted
		assertFalse(repository.findAll().iterator().hasNext());
	}
	
	
	private JavaScriptFramework createFramework(String frameworkName) {
		JavaScriptFramework f = new JavaScriptFramework();
		f.setName(frameworkName);
		JavaScriptFrameworkVersionData version1 = new JavaScriptFrameworkVersionData();
		version1.setVersion("1.0.0-b01");
		version1.setJavaScriptFramework(f);
		f.setFrameworkVersionData(Arrays.asList(version1));
		
		return f;
	}
	
	private JavaScriptFrameworkDto createFrameworkDto(String frameworkName) {
		JavaScriptFrameworkDto f = new JavaScriptFrameworkDto();
		f.setName(frameworkName);
		FrameworkVersionDataDto version1 = new FrameworkVersionDataDto();
		version1.setVersion("1.0.0-b01");
		f.setFrameworkVersionData(Arrays.asList(version1));
		
		return f;
	}
	
	private void assertFrameworkDtoResponse(JavaScriptFrameworkDto expected, JavaScriptFrameworkDto actual) {
		assertNotNull(actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getFrameworkVersionData().size(), actual.getFrameworkVersionData().size());
		FrameworkVersionDataDto frameworkVersionDataDto = actual.getFrameworkVersionData().get(0);
		assertEquals(expected.getFrameworkVersionData().get(0).getVersion(), frameworkVersionDataDto.getVersion());
		assertNotNull(frameworkVersionDataDto.getId());
	}
	
	private String mapToJson(Object obj) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
		mapper.registerModule(module);
		return mapper.readValue(json, clazz);
	}

}
