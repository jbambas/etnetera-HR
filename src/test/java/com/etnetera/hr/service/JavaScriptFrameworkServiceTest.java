package com.etnetera.hr.service;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

public class JavaScriptFrameworkServiceTest {

	private IJavaScriptFrameworkService service;
	private JavaScriptFrameworkRepository repositoryMock;
	
	@Before
	public void setUp() {
		repositoryMock = Mockito.mock(JavaScriptFrameworkRepository.class);
		service = new JavaScriptFrameworkService(repositoryMock);
	}
	
	@Test
	public void getFrameworksTest() {
		service.getFrameworks(null);
		Mockito.verify(repositoryMock, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(repositoryMock);
		
		Mockito.reset(repositoryMock);
		service.getFrameworks("JS");
		Mockito.verify(repositoryMock, Mockito.times(1)).findByName("JS");
		Mockito.verifyNoMoreInteractions(repositoryMock);
	}
	
	@Test
	public void saveFrameworkTest() {
		JavaScriptFramework f = new JavaScriptFramework();
		service.saveFramework(f);
		Mockito.verify(repositoryMock, Mockito.times(1)).save(f);
	}
	
	@Test
	public void updateFrameworkTest() {
		// attempt to call update with framework entry with null ID -> IllegalArgumentException expected
		JavaScriptFramework f = new JavaScriptFramework();
		try {
			service.updateFramework(f);
			fail("Update call with framework without ID must fail.");
		} catch(IllegalArgumentException e) {
			// ok
		}
		
		// now set ID and try it again -> it must pass
		f.setId(1L);
		service.updateFramework(f);
		Mockito.verify(repositoryMock, Mockito.times(1)).save(f);
	}
	
	@Test
	public void deleteFrameworkTest() {
		// if framework with given id does not exist -> no delete invocation
		Mockito.when(repositoryMock.existsById(1L)).thenReturn(false);
		service.deleteFramework(1L);
		Mockito.verify(repositoryMock, Mockito.times(1)).existsById(1L);
		Mockito.verify(repositoryMock, Mockito.times(0)).deleteById(1L);
		Mockito.verifyNoMoreInteractions(repositoryMock);
		
		// if framework with given id exists -> delete must be invoked
		Mockito.reset(repositoryMock);
		Mockito.when(repositoryMock.existsById(1L)).thenReturn(true);
		service.deleteFramework(1L);
		Mockito.verify(repositoryMock, Mockito.times(1)).existsById(1L);
		Mockito.verify(repositoryMock, Mockito.times(1)).deleteById(1L);
	}
}
