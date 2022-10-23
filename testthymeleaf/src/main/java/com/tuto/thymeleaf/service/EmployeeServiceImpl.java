package com.tuto.thymeleaf.service;

import com.tuto.thymeleaf.modal.Employee;
import com.tuto.thymeleaf.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeServices{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;

        if(optional.isPresent())
            employee = optional.get();
        else
            throw new RuntimeException("Emplopyee not found for id : "+id);

        return employee;
    }

    @Override
    public void deleteViaId(long id) {
        employeeRepository.deleteById(id);
    }
}
