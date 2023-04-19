package jp.co.axa.apidemo.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstants {

    public static final String COMMON_PATTERN = "^[a-z0-9-]+$";

    public static final String COMMON_PATTERN_ERROR = "must contain lower case char, digit or hyphen only";
}
