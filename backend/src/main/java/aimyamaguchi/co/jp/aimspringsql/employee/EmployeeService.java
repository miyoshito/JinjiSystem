package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.Enumeration;
import java.util.Date;

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
        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", jwtTokenProvider.createToken(username, user.getRole()));
        System.out.println(user.getShainKana());
        responseHeaders.add("Displayname", user.getShainName());
        return new ResponseEntity<>("", responseHeaders, HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY);
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

    public void insertEmployee(EmployeeMaster employee, HttpServletRequest reqs){
        if (employee.getShainId() != ""){
            employeeRepository.save(employee);
        } else {
            Long nextSeq = seq.findBySeqTablename("m_shain").getSeqValue();
            employee.setShainId(nextSeq.toString());
            employee.setShainPassword("aim123456"); //pls change this soon            
            employee.setShainRegisterDate(new Date());
            //employee.setShainRegisteredBy(jwtTokenProvider.getSubject(reqs));
            employeeRepository.save(employee);
        }        
    }


    public String gambiarra(String name){
        return name;
    }
}