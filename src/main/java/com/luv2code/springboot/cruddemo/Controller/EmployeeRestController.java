package com.luv2code.springboot.cruddemo.Controller;


import com.luv2code.springboot.cruddemo.Service.EmployeeService;
import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService  employeeService;

    //inject employee dao
    public EmployeeRestController(EmployeeService  theEmployeeService) {
        employeeService=theEmployeeService;
    }

    //expose the employee endpoints and return the list of employees
    @GetMapping("/employees")
    public List<Employee> findAll()
    {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId)
    {

        Employee theEmployee= employeeService.findById(employeeId);
        if(theEmployee==null)
        {
            throw new RuntimeException("Employee Not Found - "+ employeeId);
        }
        return theEmployee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee)
    {
        //also just in case they pass an id in JSON.... set id to 0
        //this is to force a save of new item .... instead of update.
        theEmployee.setId(0);
        employeeService.save(theEmployee);
        return theEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee)
    {
        employeeService.save(theEmployee);
        return  theEmployee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId )
    {
        Employee tempEmployee=employeeService.findById(employeeId);

        if(tempEmployee == null)
        {
            throw new RuntimeException("Employee Not Found - "+ employeeId);
        }
        employeeService.deleteById(employeeId);
        return "Deleted employee id -" + employeeId;
    }

}
