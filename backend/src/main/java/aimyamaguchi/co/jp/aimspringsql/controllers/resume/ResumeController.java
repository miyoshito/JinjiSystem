package aimyamaguchi.co.jp.aimspringsql.controllers.resume;


import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeSearchService;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeService;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins  = "*")
public class ResumeController {

    @Autowired
    private ResumeService rs;

    @Autowired
    private ResumeSearchService rss;

    @Autowired
    private EmployeeRepository er;

    @Autowired
    private SearchFilters sf;

    @Autowired
    private JwtTokenProvider jwt;

    @GetMapping("/resume/getall")
    public ResponseEntity<List<EmployeeMaster>> fullSearchResult() {
        return new ResponseEntity<>(sf.getEmployeesWithResume(), HttpStatus.OK);
    }

    @GetMapping("/resume/search")
    public ResponseEntity<List<EmployeeMaster>> searchResults(@RequestParam Map<String, String> allParams, HttpServletRequest http) {

        if (!jwt.getAreas(jwt.resolveToken(http)).contains(3)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                List<String> list = rss.getResumeSearchResults(allParams);
                return new ResponseEntity<>(sf.getEmployeesWithResume(list), HttpStatus.OK);
            } catch (CustomException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("/resume/save")
    public ResponseEntity<String> saveResume(@RequestBody ResumeModel resume, HttpServletRequest req) {
        if (!jwt.isAdmin(jwt.resolveToken(req))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                rs.saveResume(resume, req);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (CustomException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("/resume/delete")
    public ResponseEntity<String> deleteResumeDetails(@RequestParam(name = "type", required = true) String type, @RequestParam(name = "id", required = true) Long id, HttpServletRequest http) {
        if (!jwt.isAdmin(jwt.resolveToken(http))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            try {
                rs.deleteResumeDetails(type, id);
                return new ResponseEntity<>("SUCCESS!", HttpStatus.OK);
            } catch (AuthenticationException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
