package org.example;


import java.util.List;

@FunctionalInterface
public interface FilterInterface{
    public List<Employee> filter(int salary, String department, List<Employee> employees);
}
