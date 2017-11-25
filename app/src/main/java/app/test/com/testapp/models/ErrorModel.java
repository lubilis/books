package app.test.com.testapp.models;

/**
 * This data model contains information about a google api json error response
 *
 * @author omar.brugna
 */
public class ErrorModel {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
