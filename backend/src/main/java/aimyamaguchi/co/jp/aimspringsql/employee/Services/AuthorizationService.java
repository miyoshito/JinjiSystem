package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.*;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;


@Service
public class AuthorizationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SearchFilters sf;

    //login system
    public ResponseEntity<String> authenticationAttempt(String username, String password){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            EmployeeMaster user = sf.getEmployeeData(username);
            if (bCrypt.matches(password,user.getShainPassword())){
                List<Long> area = user.getAffiliation().stream().map(AFFILIATIONData::getId).collect(Collectors.toList());
                responseHeaders.add("Authorization", jwtTokenProvider.createToken(username, user.isAdmin(), area, user.getPosition().getId()));
                return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
        }  catch (AuthenticationException e) {
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean validateRequest(HttpServletRequest reqs) {
        try {
            String token = jwtTokenProvider.resolveToken(reqs);
            jwtTokenProvider.validateToken(token);          
            return true;
        } catch (AuthenticationException e) {
            return false;            
        }
    }

}