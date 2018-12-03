package aimyamaguchi.co.jp.aimspringsql.controllers;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/admin/employee-list")
    public List<EmployeeMaster> employeeList(){
        return employeeService.returnAllEmployees();
    }

    @GetMapping("/se/getmyinfos")
    public EmployeeMaster getMe(HttpServletRequest req){
        return employeeService.findMe(req);
    }

    @PostMapping("/admin/add-employee")
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
    public EmployeeMaster getProfile(@PathVariable String id){return  employeeService.getProfile(id);}

    @GetMapping("/admin/resumes/search")
    public List<EmployeeMaster> resumeSearch(
            @RequestParam (value="i", required = false) String i,
            @RequestParam (value="n", required = false)String n,
            @RequestParam (value="kat", required = false)String kat,
            @RequestParam (value="r", required = false)String r,
            @RequestParam (value="a", required = false)Long a,
            @RequestParam (value="g", required = false)String g,
            @RequestParam (value="b", required = false)String b,
            @RequestParam (value="kei", required = false)String kei,
            @RequestParam (value="s", required = false)String s
            ) {
        return employeeService.searchForResumes(i, n, kat, r, a, g, b, kei, s);
    }
}