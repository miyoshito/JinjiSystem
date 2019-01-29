package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.qualifications.QualificationsModel;
import aimyamaguchi.co.jp.aimspringsql.qualifications.QualificationsService;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class QualificationsController {

    @Autowired
    SearchFilters searchFilters;

    @Autowired
    QualificationsService qualificationsService;

    @GetMapping("/se/qualifications/search")
    public ResponseEntity<List<QualificationsModel>> searchForQualifications(@RequestParam Map<String, String> allParams, HttpServletRequest req){
        if (allParams.isEmpty()){
            return new ResponseEntity<List<QualificationsModel>>(searchFilters.getAllQualifications(), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<QualificationsModel>>(qualificationsService.qualificationSearchv2(allParams), HttpStatus.OK);
        }
    }

    @PostMapping("/se/qualifications/add")
    public ResponseEntity<String> addQualification(@RequestBody QualificationsModel qm, HttpServletRequest req){
        try {
            qualificationsService.insertAttempt(qm, req);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
