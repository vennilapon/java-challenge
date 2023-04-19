package jp.co.axa.apidemo.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import jp.co.axa.apidemo.constant.ApiConstants;
import lombok.*;

/**
 * Employee Data Transfer Object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 135641199190333712L;

    /**
     * the employee id
     */
    @NotNull
    private Long id;

    /**
     * name of the employee
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = ApiConstants.COMMON_PATTERN, message = ApiConstants.COMMON_PATTERN_ERROR)
    private String name;

    /**
     * employee salary
     */
    @NotNull
    private Integer salary;

    /**
     * employee department
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = ApiConstants.COMMON_PATTERN, message = ApiConstants.COMMON_PATTERN_ERROR)
    private String department;
}
