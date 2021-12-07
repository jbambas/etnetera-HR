package com.etnetera.hr.mapping;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersionData;
import com.etnetera.hr.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.dto.JavaScriptFrameworkDto.FrameworkVersionDataDto;

/**
 * Mapper between Domain objects and Data transfer objects - {@link JavaScriptFramework} <-> {@link JavaScriptFrameworkDto}
 * 
 * @author jbambas
 *
 */
@Component
public class JavaScriptFrameworkDtoDoMapper {

	public JavaScriptFramework mapFromDto(@NotNull JavaScriptFrameworkDto source) {
		if(source == null) {
			return null;
		}
		JavaScriptFramework target = new JavaScriptFramework();
		target.setId(source.getId());
		target.setName(source.getName());
		if(source.getFrameworkVersionData() != null) {
			List<JavaScriptFrameworkVersionData> mappedList = source.getFrameworkVersionData().stream()
					.map(d -> {
							JavaScriptFrameworkVersionData data = mapFromDto(d);
							data.setJavaScriptFramework(target);
							return data;
						})
					.collect(Collectors.toList());
			target.setFrameworkVersionData(mappedList);
		}
		return target;
	}
	
	public JavaScriptFrameworkDto mapToDto(JavaScriptFramework source) {
		if(source == null) {
			return null;
		}
		JavaScriptFrameworkDto target = new JavaScriptFrameworkDto();
		target.setId(source.getId());
		target.setName(source.getName());
		if(source.getFrameworkVersionData() != null) {
			List<FrameworkVersionDataDto> mappedList = source.getFrameworkVersionData().stream()
					.map(d -> mapToDto(d))
					.collect(Collectors.toList());
			target.setFrameworkVersionData(mappedList);
		}
		return target;
	}
	
	private JavaScriptFrameworkVersionData mapFromDto(FrameworkVersionDataDto source) {
		JavaScriptFrameworkVersionData target = new JavaScriptFrameworkVersionData();
		target.setId(source.getId());
		Optional.ofNullable(source.getDeprecationDate())
				.ifPresent(x -> target.setDeprecationDate(Timestamp.valueOf(x.atStartOfDay())));
		target.setHypeLevel(source.getHypeLevel());
		target.setVersion(source.getVersion());
		return target;
	}

	private FrameworkVersionDataDto mapToDto(JavaScriptFrameworkVersionData source) {
		FrameworkVersionDataDto target = new FrameworkVersionDataDto();
		target.setId(source.getId());
		Optional.ofNullable(source.getDeprecationDate())
				.ifPresent(x -> target.setDeprecationDate(x.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		target.setHypeLevel(source.getHypeLevel());
		target.setVersion(source.getVersion());
		return target;
	}
	
}
