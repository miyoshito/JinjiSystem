package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.Enumeration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.constants.SequenceInterface;

@Service
public class EmployeeService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SequenceInterface seq;

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
        responseHeaders.add("Authorization", jwtTokenProvider.createToken(username, user.getRole()));
        return new ResponseEntity<>("", responseHeaders, HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            //throw new CustomException("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }
    }
    //shain CRUD high level validation

    public boolean validateRequest(HttpServletRequest reqs) {
        try {
            String token = jwtTokenProvider.resolveToken(reqs);
            jwtTokenProvider.validateToken(token);          
            return true;
        } catch (AuthenticationException e) {
            return false;            
        }
    }

    public EmployeeMaster findMe(HttpServletRequest req){
        String userid = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        return employeeRepository.findByShainId(userid);
    }
    
    public List<EmployeeMaster> returnAllEmployees(){
        return employeeRepository.findAll();
    }    

    public void insertEmployee(EmployeeMaster employee, HttpServletRequest reqs){
        if (employee.getShainId() != ""){
            employeeRepository.save(employee);
        } else {
            Long nextSeq = seq.findBySeqTablename("m_shain").getSeqValue();
            employee.setShainId(nextSeq.toString());
            employee.setShainPassword("aim123456"); //pls change this soon   
            Date dt = new Date();
            employee.setShainRegisterDate(dt);
            System.out.println(employee.getShainRegisterDate());
           // employee.setShainRegisteredBy(jwtTokenProvider.getSubject(reqs));
            employeeRepository.save(employee);
        }        
    }

}