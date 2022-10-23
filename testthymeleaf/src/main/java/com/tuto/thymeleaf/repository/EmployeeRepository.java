package com.tuto.thymeleaf.repository;

import com.tuto.thymeleaf.modal.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
