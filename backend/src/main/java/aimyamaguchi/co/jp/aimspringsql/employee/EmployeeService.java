package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.*;


import javax.servlet.http.HttpServletRequest;

import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

@Service
public class EmployeeService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SequenceInterface seq;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private AffiliationRepository affiliation;
    @Autowired
    private CarmodelRepository carmodel;
    @Autowired
    private PositionRepository position;
    @Autowired
    private WorkAreaRepository workarea;

    //login system
    public ResponseEntity<String> authenticationAttempt(String username, String password){
        EmployeeMaster user = employeeRepository.findByShainId(username);
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        responseHeaders.add("Authorization", jwtTokenProvider.createToken(username, user.getRole()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
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

    public EmployeeMaster getProfile(String id){
        return employeeRepository.findByShainId(id);
    }

    public EmployeeMaster findMe(HttpServletRequest req){
        String userid = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        return employeeRepository.findByShainId(userid);
    }
    
    public List<EmployeeMaster> returnAllEmployees(){
        return employeeRepository.findAll();
    }    

    public void insertEmployee(EmployeeMaster employee, HttpServletRequest reqs){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String token = reqs.getHeader("authorization");

        if (employee.getShainId() != ""){

        if (!passwordEncoder.matches(
                employee.getPassword(),
                employeeRepository.findByShainId(employee.getShainId()).getShainPassword())){
            employee.setShainPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
        } else
            employeeRepository.save(employee);
        } else {
            Long nextSeq = seq.findBySeqTablename("m_shain").getSeqValue();
            employee.setShainId(nextSeq.toString());

            if(employee.getPassword().equals(""))
                employee.setShainPassword(passwordEncoder.encode("aim123456"));
            else
                employee.setShainPassword(passwordEncoder.encode(employee.getPassword()));
            Date dt = new Date();
            employee.setShainRegisterDate(dt);
            //employee.setShainRegisteredBy(employeeRepository.findByShainId(jwtTokenProvider.getUsername(token)).getShainName());
            employeeRepository.saveAndFlush(employee);

            ResumeModel res = new ResumeModel();
            res.setEmployee(employee);
            resumeRepository.save(res);
        }
    }


    public Map<String, Object> getEmpMasterParams(){

        Map<String,Object> map = new HashMap<>();

        map.put("AREA", workarea.findAll());
        map.put("POSITION", position.findAll());
        map.put("AFFILIATION", affiliation.findAll());
        map.put("CARMODEL", carmodel.findAll());

        return map;
    }

}