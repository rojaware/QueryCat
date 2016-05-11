package com.rojaware.query.exception;

/**
 * @author LEES4
 *
 */
public class MissingFieldError extends FieldErrorResource {

	public MissingFieldError(String field) {
		super();
		super.resource = "LSTR";
		super.code = "MISSING_FIELD";
		super.message = "is Mandatory";
		super.field = field;
		
	}
	public MissingFieldError(String field, String msg) {
		super();
		super.resource = "LSTR";
		super.code = "MISSING_FIELD";
		super.message = msg;
		super.field = field;
		
	}
}
