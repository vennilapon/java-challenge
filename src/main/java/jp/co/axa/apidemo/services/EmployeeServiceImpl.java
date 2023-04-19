package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.mapper.EmployeeMapper;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementation of employee service
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        if (optEmp.isPresent()) {
            return EmployeeMapper.INSTANCE.entityToDto(optEmp.get());
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Resource with employee id %s is not found", employeeId));
    }

    public void saveEmployee(EmployeeDTO employeeDTO) {
        Long employeeId = employeeDTO.getId();
        // when employee id is not null, check if the id is already exists if so throw exception
        if (employeeId != null) {
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);
            if (optEmp.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Resource with employee id %s is already present", employeeId));
            }
        }
        // when employee id is null consider it as new entity and save
        employeeRepository.save(EmployeeMapper.INSTANCE.dtoToEntity(employeeDTO));
    }

    public void deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Resource with employee id %s is not found", employeeId), ex);
        }
    }

    public void updateEmployee(EmployeeDTO employeeDTO) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeDTO.getId());
        if (optEmp.isPresent()) {
            employeeRepository.save(EmployeeMapper.INSTANCE.dtoToEntity(employeeDTO));
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Resource with employee id %s is not found", employeeDTO.getId()));
    }
}
