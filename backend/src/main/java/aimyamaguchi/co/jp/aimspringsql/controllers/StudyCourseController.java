package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseModel;
import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/se")
@CrossOrigin(origins  = "*")
public class StudyCourseController {

    @Autowired
    StudyCourseService scs;


    @PostMapping("/studycourse/add")
    public ResponseEntity<String> addStudyCourse(StudyCourseModel scm){
       if (scs.insertSCAttempt(scm))
           return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




}
