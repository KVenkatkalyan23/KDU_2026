package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;


public class Exercise1 {
    private static void HighEarningEngineerList(int salary, String department, List<Employee> employeeList,FilterInterface filterInterface) {
        List<Employee> filterEmployees =  filterInterface.filter(salary,department,employeeList);
        System.out.println("HighEarningEngineerList : " + filterEmployees);
    }

    private static void standardizedNameReport(){
        List<String> employeeList = Employee.getSampleData()
                .stream()
                .map(e -> e.getName().toUpperCase())
                .toList();
        System.out.println("standardizedNameReport name report list : " + employeeList);
    }

    private static void TotalAnnualSalaryBudget(){
        double sum = Employee.getSampleData()
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.println("Total sum : " + sum);

    }

    public static void run(){
        int targetSalary = 70000;
        String targetDepartment = "ENGINEERING";
        List<Employee> employeesList = Employee.getSampleData();

        FilterInterface filterInterface = (salary,department,employees) -> {
            return employees.stream()
                    .filter(o -> o.getSalary() > salary && o.getDepartment().equals(department))
                    .toList();
        };
        HighEarningEngineerList(targetSalary,targetDepartment,employeesList,filterInterface);

        standardizedNameReport();

        TotalAnnualSalaryBudget();
    }
}
