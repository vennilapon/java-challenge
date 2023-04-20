package jp.co.axa.apidemo.constant;

import lombok.experimental.UtilityClass;

/**
 * constants used in the application
 */
@UtilityClass
public class ApiConstants {

    public static final String COMMON_PATTERN = "^[A-Za-z0-9- ]+$";
    public static final String COMMON_PATTERN_ERROR =
            "must contain upper/lower case char, digit, space and hyphen only";
    public static final String COMMON_SIZE_ERROR = "size must be between 1 and 255";
    public static final String NOT_FOUND_ERROR_MESSAGE = "Resource with employee id %s is not found";
    public static final String CONFLICT_ERROR_MESSAGE = "Resource with employee id %s is already present";

    // ********************** PAGINATION CONSTANTS **********************\\

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String TOTAL_ELEMENTS = "totalElements";
    public static final String TOTAL_PAGES = "totalPages";
    public static final String NUMBER = "number";
    public static final String PAGINATION_RESULTS = "results";
}
