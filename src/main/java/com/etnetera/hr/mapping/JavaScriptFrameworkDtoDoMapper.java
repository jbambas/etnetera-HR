package com.etnetera.hr.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JavaScriptFrameworkDto;

@Component
public class JavaScriptFrameworkDtoDoMapper {

	@NotNull
	public JavaScriptFramework mapFromDto(@NotNull JavaScriptFrameworkDto javaScriptFrameworkDto) {
		JavaScriptFramework javaScriptFramework = new JavaScriptFramework();
		javaScriptFramework.setId(javaScriptFrameworkDto.getId());
		javaScriptFramework.setName(javaScriptFrameworkDto.getName());
		return javaScriptFramework;
	}
	
	@NotNull
	public JavaScriptFrameworkDto mapToDto(@NotNull JavaScriptFramework javaScriptFramework) {
		JavaScriptFrameworkDto dto = new JavaScriptFrameworkDto();
		dto.setId(javaScriptFramework.getId());
		dto.setName(javaScriptFramework.getName());
		return dto;
	}
	
}
