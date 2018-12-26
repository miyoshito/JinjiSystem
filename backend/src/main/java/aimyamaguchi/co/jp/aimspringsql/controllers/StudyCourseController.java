package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseModel;
import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseService;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class StudyCourseController {

    @Autowired
    StudyCourseService scs;

    @Autowired
    EmployeeRepository er;


    @PostMapping("/se/studycourse/add")
    public ResponseEntity<String> addStudyCourse(StudyCourseModel scm){
       if (scs.insertSCAttempt(scm))
           return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admin/studycourse/search")
    public ResponseEntity<List<EmployeeMaster>> searchSC(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "nm", required = false) String name,
            @RequestParam(value = "kn", required = false) String kana,
            @RequestParam(value = "spo", required = false) String sponsor,
            @RequestParam(value = "exp", required = false) Integer expenses,
            @RequestParam(value = "st", required = false) String start,
            @RequestParam(value = "ed", required = false) String end,
            @RequestParam(value = "op", required = false) String biop
    ){
        List<String> results = scs.searchSC(id, name, kana, sponsor, expenses, start, end, biop);
        if (results.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(er.findByShainIdIn(results), HttpStatus.OK);
    }




}
