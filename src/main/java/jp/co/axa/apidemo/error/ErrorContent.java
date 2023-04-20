package jp.co.axa.apidemo.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorContent implements Serializable {

    private static final long serialVersionUID = 5584777253377791157L;

    private final String code;

    private final String message;

    private Object detailedMessage;

    /**
     * Create a new error object with a code and a message
     *
     * @param code the error code
     * @param message the error message
     * @return a new error object
     */
    public static ErrorContent create(String code, String message) {
        return ErrorContent.builder().code(code).message(message).build();
    }

    /**
     * Create a new error object with a code, a message and some details
     *
     * @param code the error code
     * @param message the error message
     * @param detailedMessage the error details
     * @return a new error object
     */
    public static ErrorContent create(String code, String message, Object detailedMessage) {
        return ErrorContent.builder()
                .code(code)
                .message(message)
                .detailedMessage(detailedMessage)
                .build();
    }
}
