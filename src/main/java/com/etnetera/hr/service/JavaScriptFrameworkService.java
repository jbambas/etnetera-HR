package com.etnetera.hr.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    
    // ----------------------------------------------------------------------

	@Override
	public Collection<JavaScriptFramework> getFrameworks(String name) {
		List<JavaScriptFramework> results = new ArrayList<JavaScriptFramework>();
		log.debug(MessageFormat.format("Searching for frameworks, filters: {0}", (name == null ? "-" : "'" + name + "'")));
		if(name != null) {
			results.addAll(repository.findByName(name));
		} else {
			results.addAll(IteratorUtils.toList(repository.findAll().iterator()));
		}
		log.debug(MessageFormat.format("Found {0} results.", results.size()));
		return results;
	}

	@Override
	public JavaScriptFramework saveFramework(JavaScriptFramework framework) {
		log.debug("Saving new framework: " + framework.toString());
		return repository.save(framework);
	}
	
	@Override
	public JavaScriptFramework updateFramework(JavaScriptFramework framework) {
		if(framework.getId() == null) {
			throw new IllegalArgumentException("Mandatory field 'id' for update of Framework entry is not set.");
		}
		log.debug(MessageFormat.format("Updating framework with id={0}", framework.getId())); 
		return repository.save(framework);
	}

	@Override
	public void deleteFramework(long id) {
		if(repository.existsById(id)) {
			log.debug(MessageFormat.format("Deleting framework with id={0}", id));
			repository.deleteById(id);
		} else {
			log.debug(MessageFormat.format("Framework with id={0} does not exist, nothing for deletion.", id));
		}
	}
    
}
