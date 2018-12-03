package aimyamaguchi.co.jp.aimspringsql.controllers;


import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class ResumeController {

    @Autowired
    private ResumeService rs;

    @PostMapping("/resume/save")
    public ResponseEntity<String> saveResume(@RequestBody ResumeModel resume, HttpServletRequest req){
        System.out.println(resume);
        rs.saveResume(resume, req);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
