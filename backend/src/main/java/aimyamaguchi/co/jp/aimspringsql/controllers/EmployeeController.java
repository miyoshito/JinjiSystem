package aimyamaguchi.co.jp.aimspringsql.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    EmployeeRepository er;

    @GetMapping("/admin/employee-list")
    public List<EmployeeMaster> employeeList(){
        return employeeService.returnAllEmployees();
    }

    @GetMapping("/se/getmyinfos")
    public ResponseEntity<EmployeeMaster> getMe(HttpServletRequest req){
        try {
            EmployeeMaster e = employeeService.findMe(req);
            return new ResponseEntity<EmployeeMaster>(e,HttpStatus.OK);
        } catch (AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeMaster employee, HttpServletRequest req){
        System.out.println();
        
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
    public EmployeeMaster getProfile(@PathVariable String id){return  employeeService.getProfile(id);}

    @GetMapping("/admin/search-employee")
    public ResponseEntity<List<EmployeeMaster>> searchEmployee(
            @RequestParam(value="id", required = false) String id,
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="kana", required = false) String kana,
            @RequestParam(value="affiliation", required = false) List<String> affiliation){
        try {
            return new ResponseEntity<>(employeeService.searchResults(id, name, kana, affiliation), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



}