package aimyamaguchi.co.jp.aimspringsql.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.lang.CharSequence;
import java.util.stream.Collector;
import java.util.stream.Collectors;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import aimyamaguchi.co.jp.aimspringsql.constants.Sequences;
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

import static java.util.stream.Collectors.toMap;

@Service
public class EmployeeService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @PersistenceContext
    EntityManager entityManager;

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
        EmployeeMaster e = employeeRepository.findByShainId(id);
        if (e != null) {
            return e;
        } else return null;
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

        if (!employee.getShainId().equals("") && employeeRepository.findByShainId(employee.getShainId()) != null){
                employeeRepository.save(employee);
        } else {
            if (employee.getShainId().equals("")) {
                String nextSeq = seq.findBySeqTablename("m_shain").getSeqValue();
                employee.setShainId(nextSeq);
                Integer i = Integer.parseInt(nextSeq);
                i++;
                Sequences sequence = seq.findBySeqTablename("m_shain");
                sequence.setSeqValue(i.toString());
                seq.save(sequence);
            }
            if(employee.getPassword().equals("") || employee.getPassword() == null)
                employee.setShainPassword(passwordEncoder.encode("aim123456"));
            else
                employee.setShainPassword(passwordEncoder.encode(employee.getPassword()));
            Date dt = new Date();

            employee.setShainRegisterDate(dt);
            employeeRepository.save(employee);

            ResumeModel res = new ResumeModel();
            res.setEmployee(employee);
            resumeRepository.save(res);

        }
    }

    public ResponseEntity<String> changePassword(String shainid, String oldPassword, String newPassword){
        EmployeeMaster user = employeeRepository.findByShainId(shainid);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches((CharSequence) oldPassword, user.getPassword())) {
            user.setShainPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<String>resetPassword(){
        return null;
    }


    public Map<String, Object> getEmpMasterParams(){

        Map<String,Object> map = new HashMap<>();

        map.put("AREA", workarea.findAll());
        map.put("POSITION", position.findAll());
        map.put("AFFILIATION", affiliation.findAll());
        map.put("CARMODEL", carmodel.findAll());

        return map;
    }

    public List<EmployeeMaster> searchResults( String id, String name, String kana, List<String> aff){
        ArrayList<String> queryParam = new ArrayList<>();
        Optional.ofNullable(id).ifPresent((p) -> {if (p != "") queryParam.add("m.sha_no = "+p+" and");});
        Optional.ofNullable(name).ifPresent((p) -> {if (p != "") queryParam.add("m.sha_name like '%"+p+"%' and");});
        Optional.ofNullable(kana).ifPresent((p) -> {if (p != "") queryParam.add("m.sha_kana like '%"+p+"%' and");});

        if(aff.size() > 0) {
            String in = String.join(",", aff);
            queryParam.add("sho.affiliation_affiliation_id in (" + in + ") and");
        }
        String param = String.join(" ", queryParam);
        Query query = entityManager.createNativeQuery(
                "select distinct\n"
                +"m.sha_no\n"
                +"from\n"
                +"m_shain m full join sha_shozoku sho on\n"
                +"m.sha_no = sho.employee_sha_no\n"
                +"where\n "
                + param.substring(0, param.length() -4)
        );
        List<String> results = query.getResultList();
        if(results.size() > 0){
            return employeeRepository.findByShainIdIn(results);
        }
        else return null;
    }


    public int calcExperienceTime(LocalDate start, LocalDate end){
        Period period = Period.between(start, end);
        return period.getMonths();
    }

}