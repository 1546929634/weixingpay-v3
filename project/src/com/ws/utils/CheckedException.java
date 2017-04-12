package com.ws.utils;

public class CheckedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CheckedException(String message){
        super(message);
	}
	
	public CheckedException(String message, Exception e){
        super(message,e);
	}

}
