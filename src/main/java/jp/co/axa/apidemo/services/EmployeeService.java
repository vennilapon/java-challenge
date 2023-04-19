package jp.co.axa.apidemo.services;

import java.util.List;
import jp.co.axa.apidemo.dto.EmployeeDTO;

/**
 * employee service class
 */
public interface EmployeeService {

    /**
     * retrieves all the employee entities/records from database
     *
     * @return list of employees
     */
    List<EmployeeDTO> retrieveEmployees();

    /**
     * retrieves the specific employee entity/record for the given employee id
     *
     * @param employeeId the employee id to retrieve
     * @return the employee details of given employee id
     */
    EmployeeDTO getEmployee(Long employeeId);

    /**
     * saves employee to the database if not exists, returns error if the employee id is already present in database
     *
     * @param employeeDTO the employee details to be saved to database
     */
    void saveEmployee(EmployeeDTO employeeDTO);

    /**
     * Deletes the employee record from database for the given id
     *
     * @param employeeId the employee id to be deleted from the database
     */
    void deleteEmployee(Long employeeId);

    /**
     * updates the employee record to the database
     *
     * @param employeeDTO the employee record to be updated
     */
    void updateEmployee(EmployeeDTO employeeDTO);
}
