package jp.co.axa.apidemo.services;

import static jp.co.axa.apidemo.constant.ApiConstants.PAGINATION_RESULTS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Test for {@link EmployeeServiceImpl} class
 */
class EmployeeServiceImplTest {

    private EmployeeServiceImpl employeeService;

    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void beforeEach() {
        employeeService = new EmployeeServiceImpl();
        employeeRepository = mock(EmployeeRepository.class);
        employeeService.setEmployeeRepository(employeeRepository);
    }

    @Test
    void retrieveEmployees_whenCalled_success() {
        // mock employee repository find all
        Page<Employee> employeeList = new PageImpl(Arrays.asList(
                Employee.builder()
                        .id(1L)
                        .name("Vennila")
                        .department("IT")
                        .salary(12000)
                        .build(),
                Employee.builder()
                        .id(2L)
                        .name("Gowri")
                        .department("IT")
                        .salary(12000)
                        .build()));
        when(employeeRepository.findAll((Pageable) any())).thenReturn(employeeList);
        // expected result
        List<EmployeeDTO> expectedResult = List.of(
                EmployeeDTO.builder()
                        .id(1L)
                        .name("Vennila")
                        .department("IT")
                        .salary(12000)
                        .build(),
                EmployeeDTO.builder()
                        .id(2L)
                        .name("Gowri")
                        .department("IT")
                        .salary(12000)
                        .build());
        // actual method call
        Map<String, Object> actualResult = employeeService.retrieveEmployees(1, 5);

        // Assertions
        Assertions.assertEquals(actualResult.get(PAGINATION_RESULTS), expectedResult);
    }

    @Test
    void getEmployee_whenCalled_success() {
        // mock employee repository find by id
        Mockito.when(employeeRepository.findById(any()))
                .thenReturn(Optional.of(Employee.builder()
                        .id(1L)
                        .name("Vennila")
                        .department("IT")
                        .salary(12000)
                        .build()));
        // expected result
        EmployeeDTO expectedResult = EmployeeDTO.builder()
                .id(1L)
                .name("Vennila")
                .department("IT")
                .salary(12000)
                .build();
        // actual method call
        EmployeeDTO actualResult = employeeService.getEmployee(1L);
        // assertions
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void getEmployee_whenIdNotFound_responseStatusException() {
        // mock employee repository find by id
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        // assert the exception throws
        ResponseStatusException ex =
                Assertions.assertThrows(ResponseStatusException.class, () -> employeeService.getEmployee(1L));
        // Assert the exception status
        Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // Assert the reason
        Assertions.assertEquals("Resource with employee id 1 is not found", ex.getReason());
    }

    @Test
    void saveEmployee_whenCalled_success() {
        // mock employee repository save
        Mockito.when(employeeRepository.save(any()))
                .thenReturn(Employee.builder().build());
        // actual method call
        employeeService.saveEmployee(EmployeeDTO.builder()
                .id(1L)
                .name("Vennila")
                .department("IT")
                .salary(12000)
                .build());
        // verify the call
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    void saveEmployee_whenConflict_responseStatusException() {
        // mock employee repository find by id
        Mockito.when(employeeRepository.findById(any()))
                .thenReturn(Optional.of(Employee.builder().build()));
        // actual method call & assert the exception throws
        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> employeeService.saveEmployee(EmployeeDTO.builder()
                        .id(1L)
                        .name("Vennila")
                        .department("IT")
                        .salary(12000)
                        .build()));
        // Assert the exception status
        Assertions.assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        // Assert the reason
        Assertions.assertEquals("Resource with employee id 1 is already present", ex.getReason());
    }

    @Test
    void deleteEmployee_whenNotFound_responseStatusException() {
        // mock employee repository find by id
        doThrow(EmptyResultDataAccessException.class).when(employeeRepository).deleteById(any());
        // actual method call & assert the exception throws
        ResponseStatusException ex =
                Assertions.assertThrows(ResponseStatusException.class, () -> employeeService.deleteEmployee(1L));
        // Assert the exception status
        Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // Assert the reason
        Assertions.assertEquals("Resource with employee id 1 is not found", ex.getReason());
    }

    @Test
    void deleteEmployee_whenCalled_success() {
        // mock employee repository find by id
        doNothing().when(employeeRepository).deleteById(any());
        // actual method call & assert the exception throws
        Assertions.assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        // verify the call
        verify(employeeRepository, times(1)).deleteById(any());
    }
}
