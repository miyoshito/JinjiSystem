package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseInterface;
import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseModel;
import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseService;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class StudyCourseController {

    @Autowired
    private StudyCourseService scs;

    @Autowired
    private StudyCourseInterface teste;

    @Autowired
    private SearchFilters sf;

    @Autowired
    private JwtTokenProvider jwt;

        @PutMapping("/admin/studycourse/softdelete")
    public ResponseEntity<String> softDeleteSC(@RequestParam(value="id", required = true) Long id, HttpServletRequest req){
        if (!jwt.isAdmin(jwt.resolveToken(req))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (scs.softDeleteSC(id))
            return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>("NOTFOUND",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/admin/studycourse/get")
    public ResponseEntity<Optional<StudyCourseModel>> getSCModel(@RequestParam(value="id") String id){
        return new ResponseEntity<>(scs.findSCM(id),HttpStatus.OK);
    }

    @PostMapping("/se/studycourse/add")
    public ResponseEntity<String> addStudyCourse(@RequestBody StudyCourseModel scm, HttpServletRequest req){
        try {
            scs.insertSCAttempt(scm, req);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/se/studycourse/sponsorlist")
    public List<String> getStudySponsors(){
            return scs.getSCSponsorList();
    }

    @GetMapping("/se/studycourse/edulist")
    public List<String> getStudyEdu(){
            return scs.getSCEduNameList();
    }

    @GetMapping("/admin/studycourse/search")
    public ResponseEntity<List<EmployeeMaster>> searchSC(@RequestParam Map<String, String> allParams){

        if (allParams.size() == 0)
            return new ResponseEntity<>(sf.getEmployeesWithStudy(),HttpStatus.OK);
        try {
            List<String> results = scs.StudyCourseSearchResults(allParams);
            if (results.size() < 1) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sf.getEmployeesWithStudy(results), HttpStatus.OK);
        } catch (CustomException e) {
            throw e;
        }
    }




}
