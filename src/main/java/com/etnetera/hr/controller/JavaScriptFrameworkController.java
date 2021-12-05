package com.etnetera.hr.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping
	public Iterable<JavaScriptFrameworkDto> frameworks() {
		Collection<JavaScriptFramework> frameworks= service.getAllFrameworks();
		return frameworks.stream().map(f -> mapper.mapToDto(f)).collect(Collectors.toList());
	}

}
