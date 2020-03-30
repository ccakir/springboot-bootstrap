package com.cakir.web.error;


public final class UserAlreadyExistException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4738047647736818947L;

	public UserAlreadyExistException() {
		super();
		
	}

	

	public UserAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserAlreadyExistException(String message) {
		super(message);
		
	}

	public UserAlreadyExistException(Throwable cause) {
		super(cause);
		
	}
	
	

}
