package jp.co.axa.apidemo.constant;

import lombok.experimental.UtilityClass;

/**
 * Path constants
 */
@UtilityClass
public class Path {

    /**
     * path separator
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * api constant
     */
    public static final String API = "api";

    /**
     * version constant
     */
    public static final String VERSION = "v1";

    /**
     * API Path (/api/v1)
     */
    public static final String API_PATH = PATH_SEPARATOR + API + PATH_SEPARATOR + VERSION;

    /**
     * employees base path (/employees)
     */
    public static final String EMPLOYEES_BASE_PATH = PATH_SEPARATOR + "employees";

    /**
     * employee Path (/employees/{employeeId})
     */
    public static final String EMPLOYEE_PATH = EMPLOYEES_BASE_PATH + PATH_SEPARATOR + "{employeeId}";
}
