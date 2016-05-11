/**
 * 
 */
package com.rojaware.query.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * ErrorHandling for Web -
 *  @see <a href="http://springinpractice.com/2013/10/09/generating-json-error-object-responses-with-spring-web-mvc"> Generating JSON Error Object Responses With Spring Web MVC</a> 
 * {
  "code": "InvalidRequest",
  "message": "Invalid doodad",
  "fieldErrors": [
    {
      "resource": "doodadResource",
      "field": "key",
      "code": "NotNull",
      "message": "may not be null"
    },
    {
      "resource": "doodadResource",
      "field": "name",
      "code": "NotNull",
      "message": "may not be null"
    }
  ]
}
 * @author lees4
 *
 */
public class ErrorResource {
    private String code;
    private String message;
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource() {
    	super();
    	code = "ERROR"; // default code 
    }

    /**
	 * @param code
	 */
	public ErrorResource(String code) {
		super();
		this.code = code;
	}

	public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public List<FieldErrorResource> getFieldErrors() {
    	if (fieldErrors == null) fieldErrors = new ArrayList<FieldErrorResource>();
    	return fieldErrors; 
    }

    public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}