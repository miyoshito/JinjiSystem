package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.lang.CharSequence;
import java.util.stream.Collectors;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import aimyamaguchi.co.jp.aimspringsql.constants.Sequences;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.*;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.constants.SequenceInterface;

import static java.util.stream.Collectors.toMap;

@Service
public class AuthorizationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmployeeRepository employeeRepository;

    //login system
    public ResponseEntity<String> authenticationAttempt(String username, String password){
        EmployeeMaster user = employeeRepository.findByShainId(username);
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        List<Long> area = user.getAffiliation().stream().map(AFFILIATIONData::getId).collect(Collectors.toList());
        responseHeaders.add("Authorization", jwtTokenProvider.createToken(username, user.getRole(), area, user.getPosition().getId()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            //throw new CustomException("Invalid username/password", HttpStatus.UNAUTHORIZED);
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

    //Funcao global que retorna 1 user sem nada?
    public EmployeeMaster getProfile(String id){
        EmployeeMaster e = employeeRepository.findByShainId(id);
        if (e != null) {
            return e;
        } else return null;
    }


/*    public int calcExperienceTime(LocalDate start, LocalDate end){
        Period period = Period.between(start, end);
        return period.getMonths();
    }*/

}