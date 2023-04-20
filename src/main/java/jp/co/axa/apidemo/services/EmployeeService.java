package jp.co.axa.apidemo.services;

import java.util.Map;
import jp.co.axa.apidemo.dto.EmployeeDTO;

/**
 * employee service class
 */
public interface EmployeeService {

    /**
     * retrieves all the employee entities/records from database
     *
     * @param page the page to be retrieved
     * @param size the size of total elements in a page
     * @return list of employees
     */
    Map<String, Object> retrieveEmployees(int page, int size);

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
     * @return the saved record/entity
     */
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    /**
     * Deletes the employee record from database for the given id
     *
     * @param employeeId the employee id to be deleted from the database
     */
    void deleteEmployee(Long employeeId);

    /**
     * updates the employee record to the database for the given employee id
     *
     * @param employeeId the employee id for which the record is to be updated
     * @param employeeDTO the employee record to be updated
     * @return the updated record details
     */
    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO);
}
