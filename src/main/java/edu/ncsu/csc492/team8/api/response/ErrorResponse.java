/**
 * 
 */
package edu.ncsu.csc492.team8.api.response;

/**
 * @author Brent Younce <bjyounce@ncsu.edu>
 *
 */
public class ErrorResponse {
    /**
     * @param http_error_code
     * @param error_message
     */
    public ErrorResponse ( String http_error_code, String error_message ) {
        setHttp_error_code(http_error_code);
        setError_message(error_message);
    }
    /**
     * @return the http_error_code
     */
    public String getHttp_error_code () {
        return http_error_code;
    }
    /**
     * @param http_error_code the http_error_code to set
     */
    public void setHttp_error_code ( String http_error_code ) {
        this.http_error_code = http_error_code;
    }
    /**
     * @return the error_message
     */
    public String getError_message () {
        return error_message;
    }
    /**
     * @param error_message the error_message to set
     */
    public void setError_message ( String error_message ) {
        this.error_message = error_message;
    }
    String http_error_code;
    String error_message;
}
