package dev.patika.patika0601.service;

import dev.patika.patika0601.exception.EmployeeIsAlreadyExistException;
import dev.patika.patika0601.exception.EmployeeNotFoundException;
import dev.patika.patika0601.model.Employee;
import dev.patika.patika0601.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees(){
        return repository.findAll();
    }

    public void addEmployee(Employee employee) {
        // TO-DO check if employee exists ???
        if (repository.existsByEmail(employee.getEmail())){
            throw new EmployeeIsAlreadyExistException("Employee with email:" + employee.getEmail() + " is already exist.") ;
        }
        repository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        // TO-DO check if employee exists with given ID???
        if(repository.existsById(employeeId)){
            repository.deleteById(employeeId);
        }
        else{
            throw new EmployeeNotFoundException("Employee with id:" + employeeId + " not found.");
        }

    }
}
