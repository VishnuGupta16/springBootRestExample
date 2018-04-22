package org.vg.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
	
	private String message;
	private String detail;
	private LocalDateTime time;
	
	public ExceptionResponse(String message, String detail, LocalDateTime time) {
		super();
		this.message = message;
		this.detail = detail;
		this.time = time;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	

}
