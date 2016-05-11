/**
 * 
 */
package com.rojaware.query.exception;

/**
 * @author lees4
 *
 */
public class MessagingException extends AppException {

	/**
	 * @param p_exceptionMsg
	 * @param type
	 */
	public MessagingException(String p_exceptionMsg, String type) {
		super(p_exceptionMsg, type);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param p_exceptionMsg
	 */
	public MessagingException(String p_exceptionMsg) {
		super(p_exceptionMsg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param p_exceptionMsg
	 * @param cause
	 */
	public MessagingException(String p_exceptionMsg, Throwable cause) {
		super(p_exceptionMsg, cause);
		// TODO Auto-generated constructor stub
	}

}
