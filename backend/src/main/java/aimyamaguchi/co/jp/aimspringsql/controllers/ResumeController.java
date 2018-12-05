package aimyamaguchi.co.jp.aimspringsql.controllers;


import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeService;
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

    @GetMapping("/resume/search")
    public List<EmployeeMaster> searchResults(
            @RequestParam (value="i", required = false) String id,
            @RequestParam (value="n", required = false) String name,
            @RequestParam (value="k", required = false) String kata,
            @RequestParam (value="r", required = false) String recruit,
            @RequestParam (value="a", required = false) String age,
            @RequestParam (value="st", required = false)String study,
            @RequestParam (value="b", required = false) String bunri,
            @RequestParam (value="ca", required = false)String career,
            @RequestParam (value="qq", required = false)String qualification){
        List<String> list = rs.searchQueryBuilder(id, name, kata, recruit, age, study, bunri, career, qualification);
        System.out.println(list);

        return er.findByShainIdIn(list);
    }

    @PostMapping("/resume/save")
    public ResponseEntity<String> saveResume(@RequestBody ResumeModel resume, HttpServletRequest req){
        System.out.println(resume);
        rs.saveResume(resume, req);
        return new ResponseEntity<>("OK", HttpStatus.OK);
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
