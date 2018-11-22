package aimyamaguchi.co.jp.aimspringsql.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/admin/employee-list")
    public List<EmployeeMaster> employeeList(){
        return employeeService.returnAllEmployees();
    }

    @GetMapping("/se/getmyinfos")
    public EmployeeMaster getMe(HttpServletRequest req){
        return employeeService.findMe(req);
    }

    @PostMapping(value="/admin/add-employee", produces="application/json; charset=utf-8")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeMaster employee, HttpServletRequest req){
        
    try {
        if(employeeService.validateRequest(req)) {
            employeeService.insertEmployee(employee, req);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (AuthenticationException e) {
        throw new CustomException("You don't have authorities for do this.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/admin/getprofile/{id}")
    public EmployeeMaster getProfile(@PathVariable String id){
        return  employeeService.getProfile(id);
    }
}