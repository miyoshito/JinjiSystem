package aimyamaguchi.co.jp.aimspringsql.controllers.security;


import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
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
    public ResponseEntity<String> loginAttempt(@RequestParam String username, @RequestParam String password) {
        return authorizationService.authenticationAttempt(username, password);

    }

    @PutMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest req){
        if(jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req)).equals("ADMIN")){
            //it means he can resetpassword
            System.out.println("reset password logic goes there");
            return null;
        } else {
            System.out.println(jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req)));
         return null;
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
