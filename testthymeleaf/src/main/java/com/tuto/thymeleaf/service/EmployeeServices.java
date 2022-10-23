package com.tuto.thymeleaf.service;

import com.tuto.thymeleaf.modal.Employee;

import java.util.List;

public interface EmployeeServices {
    List<Employee> getAllEmployee();
    void save(Employee employee);
    Employee getById(Long id);
    void deleteViaId(long id);
}
