package org.vg.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -537195494277136619L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
