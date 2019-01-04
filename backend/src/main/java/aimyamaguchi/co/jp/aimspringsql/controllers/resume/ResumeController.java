package aimyamaguchi.co.jp.aimspringsql.controllers.resume;


import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeService;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins  = "*")
public class ResumeController{

    @Autowired
    private ResumeService rs;

    @Autowired
    private EmployeeRepository er;

    @Autowired
    private SearchFilters sf;

    @GetMapping("/resume/search")
    public ResponseEntity<List<EmployeeMaster>> searchResults(
            @RequestParam (value="i", required = false) String id,
            @RequestParam (value="n", required = false) String name,
            @RequestParam (value="k", required = false) String kata,
            @RequestParam (value="r", required = false) String recruit,
            @RequestParam (value="a", required = false) String age,
            @RequestParam (value="st", required = false)String study,
            @RequestParam (value="b", required = false) String bunri,
            @RequestParam (value="ca", required = false)String career,
            @RequestParam (value="qq", required = false)String qualification){
        try {
            List<String> list = rs.searchQueryBuilder(id, name, kata, recruit, age, study, bunri, career, qualification);
            return new ResponseEntity<>(sf.getEmployeesWithResume(list), HttpStatus.OK);
        } catch (CustomException e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resume/save")
    public ResponseEntity<String> saveResume(@RequestBody ResumeModel resume, HttpServletRequest req){
        System.out.println(resume);
        try {
            rs.saveResume(resume, req);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/resume/delete")
    public ResponseEntity<String> deleteResumeDetails(@RequestParam(name="type", required = true) String type, @RequestParam(name="id", required = true) Long id){
        try {
            rs.deleteResumeDetails(id, type);
            return new ResponseEntity<>("SUCCESS!", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("SUCCESS!", HttpStatus.UNAUTHORIZED);
        }
    }
}
