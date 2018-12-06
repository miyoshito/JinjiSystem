package aimyamaguchi.co.jp.aimspringsql.controllers;


import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins  = "*")
public class AuthenticationController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<String> loginAttempt(@RequestParam String username, @RequestParam String password) {
        return employeeService.authenticationAttempt(username, password);

    }




}
