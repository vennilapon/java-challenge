package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Employee Jpa repository
 */
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {}
