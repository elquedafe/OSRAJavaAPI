package org.osra.tools;

public class RestResponse {
	private String message;
	private int code;
	/**
	 * @param message
	 * @param code
	 */
	public RestResponse(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}
	/**
	 * 
	 */
	public RestResponse() {
		super();
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
}
