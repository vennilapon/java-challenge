package jp.co.axa.apidemo.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic exception handler class for the whole application
 */
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        String error = "Validation failed for the input fields";
        return buildErrorContent(
                HttpStatus.BAD_REQUEST, error, ex.getBindingResult().getAllErrors());
    }

    /**
     * Handles the {@link ResponseStatusException} error
     *
     * @param ex the response status exception which is thrown at any point in the application
     * @return the response entity object with error/exception details in it
     */
    @ExceptionHandler(value = {ResponseStatusException.class})
    private ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return buildErrorContent(ex.getStatus(), ex.getReason(), null);
    }

    /**
     * customized error handling mechanism for the better readability
     *
     * @param httpStatus the https status value
     * @param errorMessage error message of exception
     * @param detailedErrorMessage of exception if available
     * @return response entity with Error content details
     */
    private ResponseEntity<Object> buildErrorContent(
            HttpStatus httpStatus, String errorMessage, Object detailedErrorMessage) {
        ErrorContent ec = ErrorContent.create(httpStatus.toString(), errorMessage, detailedErrorMessage);
        return new ResponseEntity<>(ec, httpStatus);
    }
}
