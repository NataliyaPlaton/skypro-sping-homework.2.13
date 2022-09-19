package pro.sky.skyprospinghomework23.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.skyprospinghomework23.exception.EmployeeNotFoundException;
import pro.sky.skyprospinghomework23.model.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Василий", "Васильев", 1, 55_000),
                new Employee("Андрей", "Андреев", 1, 65_000),
                new Employee("Иван", "Иванов", 2, 45_000),
                new Employee("Мария", "Иванова", 2, 50_000),
                new Employee("Ирина", "Андреева", 2, 47_000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    public void findEmployeeByDepartmentNegativeTest() {
        Assertions.assertThat(departmentService.findEmployeeFromDepartment(4)).isEmpty();
    }

    @Test
    public void withNoEmployees() {
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        Assertions.assertThat(departmentService.findEmployee()).isEmpty();
        Assertions.assertThat(departmentService.findEmployeeFromDepartment(1)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("findEmployeeWithMaxSalaryFromDepartmentParams")
    public void findEmployeeWithMaxSalaryFromDepartmentPositiveTest(int department, Employee expected) {
        assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(department)).
                isEqualTo(expected);
    }

    @Test
    public void findEmployeeWithMaxSalaryFromDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("findEmployeeWithMinSalaryFromDepartmentParams")
    public void findEmployeeWithMinSalaryFromDepartmentPositiveTest(int department, Employee expected) {
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(department)).
                isEqualTo(expected);
    }

    @Test
    public void findEmployeeWithMinSalaryFromDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("findEmployeeFromDepartmentParams")
    public void findEmployeeFromDepartmentPositiveTest(int department, List<Employee> expected) {
        Assertions.assertThat(departmentService.findEmployeeFromDepartment(department)).
                containsExactlyElementsOf(expected);
    }

    @Test
    public void findEmployeeTest() {
        Assertions.assertThat(departmentService.findEmployee()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Василий", "Васильев", 1, 55_000),
                                new Employee("Андрей", "Андреев", 1, 65_000)),
                        2, List.of(new Employee("Иван", "Иванов", 2, 45_000),
                                new Employee("Мария", "Иванова", 2, 50_000),
                                new Employee("Ирина", "Андреева", 2, 47_000))
                )
        );
    }

    public static Stream<Arguments> findEmployeeWithMaxSalaryFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Андрей", "Андреев", 1, 65_000)),
                Arguments.of(2, new Employee("Мария", "Иванова", 2, 50_000))
        );
    }

    public static Stream<Arguments> findEmployeeWithMinSalaryFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Василий", "Васильев", 1, 55_000)),
                Arguments.of(2, new Employee("Иван", "Иванов", 2, 45_000))
        );
    }

    public static Stream<Arguments> findEmployeeFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Василий", "Васильев", 1, 55_000),
                        new Employee("Андрей", "Андреев", 1, 65_000))),
                Arguments.of(2, List.of(new Employee("Иван", "Иванов", 2, 45_000),
                        new Employee("Мария", "Иванова", 2, 50_000),
                        new Employee("Ирина", "Андреева", 2, 47_000))),
                Arguments.of(3, Collections.emptyList())
        );
    }
}