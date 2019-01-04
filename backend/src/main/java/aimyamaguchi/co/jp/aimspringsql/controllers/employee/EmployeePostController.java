package aimyamaguchi.co.jp.aimspringsql.controllers.employee;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.EmployeeInsertFunctions;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.EmployeeSearchFunctions;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.AuthorizationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class EmployeePostController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private EmployeeInsertFunctions esc;

    @Autowired
    private JwtTokenProvider jwtValidator;

    @PostMapping("/admin/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeMaster employee, HttpServletRequest req){
        System.out.println();
        
    try {
        if(authorizationService.validateRequest(req)) {
            esc.insertEmployee(employee, req);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (AuthenticationException e) {
        throw new CustomException("You don't have authorities for do this.", HttpStatus.UNAUTHORIZED);
        }
    }





}