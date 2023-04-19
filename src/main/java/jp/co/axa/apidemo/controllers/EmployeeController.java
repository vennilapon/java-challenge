package jp.co.axa.apidemo.controllers;

import java.util.List;
import javax.validation.Valid;
import jp.co.axa.apidemo.constant.Path;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Employee rest controller
 */
@Slf4j
@RestController
@RequestMapping(Path.API_PATH)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(Path.EMPLOYEES_BASE_PATH)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EmployeeDTO> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping(Path.EMPLOYEE_PATH)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmployeeDTO getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping(Path.EMPLOYEES_BASE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void saveEmployee(@RequestBody @Valid EmployeeDTO employee) {
        employeeService.saveEmployee(employee);
        log.info("Employee, id: {} Saved Successfully", employee.getId());
    }

    @DeleteMapping(Path.EMPLOYEE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        log.info("Employee, id: {} Deleted Successfully", employeeId);
    }

    @PutMapping(Path.EMPLOYEE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateEmployee(
            @RequestBody @Valid EmployeeDTO employee, @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployee(employee);
        log.info("Employee, id: {} Updated Successfully", employeeId);
    }
}
