package com.etnetera.hr.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.mapping.JavaScriptFrameworkDtoDoMapper;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.IJavaScriptFrameworkService;
import com.etnetera.hr.service.JavaScriptFrameworkService;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
@RequestMapping(path = "/frameworks")
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkDtoDoMapper mapper;
	private final IJavaScriptFrameworkService service;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkDtoDoMapper mapper, IJavaScriptFrameworkService service) {
		this.mapper = mapper;
		this.service = service;
	}
	
    // ----------------------------------------------------------------------

	/**
	 * Search for frameworks. Option to filter results by name.
	 */
	@GetMapping
	public Iterable<JavaScriptFrameworkDto> searchFramework(@RequestParam(name = "name", required = false) String name) {
		Collection<JavaScriptFramework> frameworks= service.getFrameworks(name);
		return frameworks.stream().map(f -> mapper.mapToDto(f)).collect(Collectors.toList());
	}
	
	/**
	 * Insert new framework.
	 */
	@PostMapping
	public JavaScriptFrameworkDto insertFramework(@Valid @RequestBody JavaScriptFrameworkDto dto) {
		JavaScriptFramework f = mapper.mapFromDto(dto);
		f = service.saveFramework(f);
		return mapper.mapToDto(f);
	}
	
	/**
	 * Update (or insert if does not exist yet) framework.
	 */
	@PutMapping
	public JavaScriptFrameworkDto upsertFramework(@Valid @RequestBody JavaScriptFrameworkDto dto) {
		JavaScriptFramework f = mapper.mapFromDto(dto);
		if(dto.getId() == null) {
			// if no ID, then new entry -> create new
			f = service.saveFramework(f);
			return mapper.mapToDto(f);
		} else {
			// if ID is already set, then update entry
			f = service.updateFramework(f);
			return mapper.mapToDto(f);
		}
	}
	
	/**
	 * Delete Framework with given id.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteFramework(@PathVariable long id) {
		service.deleteFramework(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
