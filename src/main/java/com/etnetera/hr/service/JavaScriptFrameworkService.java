package com.etnetera.hr.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

@Service
public class JavaScriptFrameworkService implements IJavaScriptFrameworkService {
	
	private static final Logger log = LoggerFactory.getLogger(JavaScriptFrameworkService.class);
	
	private final JavaScriptFrameworkRepository repository;

    @Autowired
    public JavaScriptFrameworkService(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

	@Override
	public @NotNull Collection<JavaScriptFramework> getAllFrameworks() {
		log.debug("Finding all known frameworks");
		List<JavaScriptFramework> results = IteratorUtils.toList(repository.findAll().iterator());
		log.debug(MessageFormat.format("Found {0} results.", results.size()));
		return results;
	}
    
}
