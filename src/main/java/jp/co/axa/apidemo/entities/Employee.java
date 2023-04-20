package jp.co.axa.apidemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

/**
 * The entity class for the dto {@link jp.co.axa.apidemo.dto.EmployeeDTO} and is mapped by {@link jp.co.axa.apidemo.mapper.EmployeeMapper}
 */
@Data
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Column(name = "EMPLOYEE_SALARY")
    private Integer salary;

    @Column(name = "DEPARTMENT")
    private String department;
}
