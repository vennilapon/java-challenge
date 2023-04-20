package jp.co.axa.apidemo.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Map;
import javax.validation.Valid;
import jp.co.axa.apidemo.constant.Path;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Employee rest controller class
 */
@Slf4j
@RestController
@RequestMapping(Path.API_PATH)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * fetches all the employee entities/records from database with pagination
     *
     * @param page the page to be fetched
     * @param size the no. of elements on the page
     * @return list of employees with status code 200 if success
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Read Successful"),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized"),
                @ApiResponse(responseCode = "403", description = "Forbidden"),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal Server error")
            })
    @GetMapping(Path.EMPLOYEES_BASE_PATH)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getEmployees(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(employeeService.retrieveEmployees(page, size), HttpStatus.OK);
    }

    /**
     * Get employee for the given employee id from database
     *
     * @param employeeId the employee id for which the data is to be read
     * @return the requested employee record for the given id
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Read Successful"),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized"),
                @ApiResponse(responseCode = "403", description = "Forbidden"),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal Server error")
            })
    @GetMapping(Path.EMPLOYEE_PATH)
    @ResponseBody
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    /**
     * validates the input and saves the employee details to the database
     *
     * @param employee the dto containing employee details to be saved in database
     * @return the http status code 201 if created successful
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Create success"),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized"),
                @ApiResponse(responseCode = "403", description = "Forbidden"),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal Server error")
            })
    @PostMapping(Path.EMPLOYEES_BASE_PATH)
    @ResponseBody
    public ResponseEntity<Void> saveEmployee(@RequestBody @Valid EmployeeDTO employee) {
        employeeService.saveEmployee(employee);
        log.info("Employee, id: {} Saved Successfully", employee.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * deletes an employee record from the database
     *
     * @param employeeId the employee id for which the data to be deleted
     * @return the 204 http status code if record is deleted
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Delete success"),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized"),
                @ApiResponse(responseCode = "403", description = "Forbidden"),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal Server error")
            })
    @DeleteMapping(Path.EMPLOYEE_PATH)
    @ResponseBody
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        log.info("Employee, id: {} Deleted Successfully", employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * validates the input and updates employee details to the database
     *
     * @param employeeId the employee id for which the data to be updated
     * @param employee the actual values to be replaced with the database values
     * @return the 204 http status code if created success
     */
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "204", description = "Update success"),
                @ApiResponse(responseCode = "400", description = "Bad Request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized"),
                @ApiResponse(responseCode = "403", description = "Forbidden"),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal Server error")
            })
    @PutMapping(Path.EMPLOYEE_PATH)
    @ResponseBody
    public ResponseEntity<Void> updateEmployee(
            @RequestBody @Valid EmployeeDTO employee, @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployee(employeeId, employee);
        log.info("Employee, id: {} Updated Successfully", employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
