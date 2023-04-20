package jp.co.axa.apidemo.dto;

import static jp.co.axa.apidemo.constant.ApiConstants.COMMON_PATTERN_ERROR;
import static jp.co.axa.apidemo.constant.ApiConstants.COMMON_SIZE_ERROR;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import jp.co.axa.apidemo.constant.ApiConstants;
import lombok.*;

/**
 * Employee Data Transfer Object and is mapped to {@link jp.co.axa.apidemo.entities.Employee} by mapper {@link jp.co.axa.apidemo.mapper.EmployeeMapper}
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
    @Size(min = 1, max = 255, message = COMMON_SIZE_ERROR)
    @Pattern(regexp = ApiConstants.COMMON_PATTERN, message = COMMON_PATTERN_ERROR)
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
    @Size(min = 1, max = 255, message = COMMON_SIZE_ERROR)
    @Pattern(regexp = ApiConstants.COMMON_PATTERN, message = COMMON_PATTERN_ERROR)
    private String department;
}
