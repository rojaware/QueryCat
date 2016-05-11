/**
 * 
 */
package com.rojaware.query.exception;

/**
 * @author lees4
 *
 */
public class FieldErrorResource {
    protected String resource;
    protected String field;
    protected String code;
    protected String message;

    /**
	 * @param resource
	 * @param field
	 * @param code
	 * @param message
	 */
	public FieldErrorResource( String field) {
		super();
		
		this.field = field;
	}

	public FieldErrorResource() {
		// TODO Auto-generated constructor stub
	}

	public String getResource() { return resource; }

    public void setResource(String resource) { this.resource = resource; }

    public String getField() { return field; }

    public void setField(String field) { this.field = field; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}