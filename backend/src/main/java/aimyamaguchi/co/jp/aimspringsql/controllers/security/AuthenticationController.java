package aimyamaguchi.co.jp.aimspringsql.controllers.security;


import aimyamaguchi.co.jp.aimspringsql.AdministratorModel;
import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.LoginModel;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.AuthorizationService;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.EmployeeUpdateFunctions;
import aimyamaguchi.co.jp.aimspringsql.util.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins  = "*")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private EmployeeUpdateFunctions euf;

    @Autowired
    private AdministratorService admin;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> loginAttempt(@RequestBody LoginModel login) {
        return authorizationService.authenticationAttempt(login.getUsername(), login.getPassword());
    }

    @PutMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam(value="id", required = true) String id, HttpServletRequest req){
        if(jwtTokenProvider.isAdmin(jwtTokenProvider.resolveToken(req))){
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
    	if(euf.changePassword(id, oldp, newp)) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @GetMapping("/loaduserpermissions")
    public ResponseEntity<List<AdministratorModel>> getUsersPermission(
            @RequestParam(name = "id", required = false) Optional<String> id
    ){
        return id.map(s -> new ResponseEntity<>(admin.listUsersAndPermissionsWithId(s), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(admin.listUsersAndPermissions(), HttpStatus.OK));
    }

    @PutMapping("/changeuserpermissions")
    public ResponseEntity<String> updateUserPermissions(
            @RequestBody List<AdministratorModel> users,
            HttpServletRequest req
    ){
        try {
            if (jwtTokenProvider.isAdmin(jwtTokenProvider.resolveToken(req))
                    && jwtTokenProvider.getAreas(jwtTokenProvider.resolveToken(req)).contains(3)) {
                admin.updateUserPermissions(users);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (CustomException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }






}
