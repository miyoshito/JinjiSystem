package aimyamaguchi.co.jp.aimspringsql.controllers.security;


import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.LoginModel;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.AuthorizationService;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.EmployeeUpdateFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins  = "*")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private EmployeeUpdateFunctions euf;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> loginAttempt(@RequestBody LoginModel login) {
        return authorizationService.authenticationAttempt(login.getUsername(), login.getPassword());
    }

    @PutMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam(value="id", required = true) String id, HttpServletRequest req){
        if(jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req)).equals("ADMIN")){
            euf.resetPassword(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/changepassword")
    public ResponseEntity<String> changePassword(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "opw", required = true) String oldp,
            @RequestParam(value = "npw", required = true) String newp,
            HttpServletRequest req){
        if (jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)).equals(id)){
            return euf.changePassword(id, oldp, newp);
        } else {
            return new ResponseEntity<>("NULL", HttpStatus.UNAUTHORIZED);
        }
    }






}
