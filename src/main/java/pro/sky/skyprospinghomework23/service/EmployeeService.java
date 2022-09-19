package pro.sky.skyprospinghomework23.service;

import org.springframework.stereotype.Service;
import pro.sky.skyprospinghomework23.exception.EmployeeAlreadyAddedException;
import pro.sky.skyprospinghomework23.exception.EmployeeNotFoundException;
import pro.sky.skyprospinghomework23.exception.EmployeeStorageIsFullException;
import pro.sky.skyprospinghomework23.model.Employee;

import java.util.*;

@Service
public class EmployeeService {
    private static final int LIMIT = 10;
    private final List<Employee> employees = new ArrayList<>();
    private final ValidatorService validatorService;

    public EmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public Employee add(String name,
                        String surname,
                        int department,
                        double salary) {
        Employee employee = new Employee(
                validatorService.validateName(name),
                validatorService.validateSurname(surname),
                department,
                salary);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String name,
                           String surname) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getName().equals(name) && emp.getSurname().equals(surname))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        employees.remove(employee);
        return employee;

    }

    public Employee find(String name,
                         String surname) {
        return employees.stream()
                .filter(employee -> employee.getName().equals(name) && employee.getSurname().equals(surname))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }
}


