package jp.co.axa.apidemo.services;

import static jp.co.axa.apidemo.constant.ApiConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.mapper.EmployeeMapper;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementation of employee service
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * the cache name of spring framework in memory cache
     */
    private static final String EMPLOYEES_CACHE = "employees";

    /**
     * the cache key for the cache name "employee"
     */
    private static final String EMPLOYEES_CACHE_KEY = "#employeeId";

    /**
     * the sorting field for the pagination result
     */
    private static final String PAGINATION_SORTING_FIELD = "id";

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * retrieves list of employees from the database for the given page and size
     *
     * @param page the page to be retrieved
     * @param size the size of total elements in a page
     * @return the Pageable result with metadata containing current page, total elements, total pages
     */
    @Cacheable(cacheNames = EMPLOYEES_CACHE)
    public Map<String, Object> retrieveEmployees(int page, int size) {

        Page<Employee> employees =
                employeeRepository.findAll(PageRequest.of(page, size, Sort.by(PAGINATION_SORTING_FIELD)));
        List<EmployeeDTO> employeeDTOList =
                employees.stream().map(EmployeeMapper.INSTANCE::entityToDto).collect(Collectors.toList());
        // meta data for pagination result
        Map<String, Long> metadata = new HashMap<>();
        metadata.put(SIZE, (long) size);
        metadata.put(TOTAL_ELEMENTS, employees.getTotalElements());
        metadata.put(TOTAL_PAGES, (long) employees.getTotalPages());
        metadata.put(NUMBER, (long) employees.getNumber());

        // response object creation
        Map<String, Object> response = new HashMap<>();
        response.put(PAGINATION_RESULTS, employeeDTOList);
        response.put(PAGE, metadata);
        return response;
    }

    /**
     * retrieves the specific employee entity/record for the given employee id
     *
     * @param employeeId the employee id to retrieve
     * @return the employee details of given employee id
     */
    @Cacheable(cacheNames = EMPLOYEES_CACHE, key = EMPLOYEES_CACHE_KEY)
    public EmployeeDTO getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        if (optEmp.isPresent()) {
            return EmployeeMapper.INSTANCE.entityToDto(optEmp.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(NOT_FOUND_ERROR_MESSAGE, employeeId));
    }

    /**
     * saves employee to the database if not exists, returns error if the employee id is already present in database
     *
     * @param employeeDTO the employee details to be saved to database
     * @return the saved record/entity
     */
    @CacheEvict(cacheNames = EMPLOYEES_CACHE, allEntries = true)
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Long employeeId = employeeDTO.getId();
        // when employee id is not null, check if the id is already exists if so throw exception
        if (employeeId != null) {
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);
            if (optEmp.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, String.format(CONFLICT_ERROR_MESSAGE, employeeId));
            }
        }
        // when employee id is null consider it as new entity and save
        Employee employee = employeeRepository.save(EmployeeMapper.INSTANCE.dtoToEntity(employeeDTO));
        return EmployeeMapper.INSTANCE.entityToDto(employee);
    }

    /**
     * Deletes the employee record from database for the given id
     *
     * @param employeeId the employee id to be deleted from the database
     */
    @CacheEvict(cacheNames = EMPLOYEES_CACHE, key = EMPLOYEES_CACHE_KEY, allEntries = true)
    public void deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format(NOT_FOUND_ERROR_MESSAGE, employeeId), ex);
        }
    }

    /**
     * updates the employee record to the database for the given employee id
     *
     * @param employeeId the employee id for which the record is to be updated
     * @param employeeDTO the employee record to be updated
     * @return the updated record details
     */
    @CachePut(cacheNames = EMPLOYEES_CACHE, key = EMPLOYEES_CACHE_KEY)
    @CacheEvict(cacheNames = EMPLOYEES_CACHE, allEntries = true)
    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        // update for the employee id in path variable
        employeeDTO.setId(employeeId);
        Optional<Employee> optEmp = employeeRepository.findById(employeeDTO.getId());
        // when employee id not found, throw NOT FOUND exception
        if (optEmp.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format(NOT_FOUND_ERROR_MESSAGE, employeeDTO.getId()));
        }
        Employee employee = employeeRepository.save(EmployeeMapper.INSTANCE.dtoToEntity(employeeDTO));
        return EmployeeMapper.INSTANCE.entityToDto(employee);
    }
}
