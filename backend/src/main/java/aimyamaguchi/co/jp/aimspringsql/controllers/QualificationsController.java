package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
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

    @GetMapping("/public/qualifications/search")
    public ResponseEntity<List<EmployeeMaster>> searchForQualifications(@RequestParam Map<String, String> allParams, HttpServletRequest req){
        if (allParams.isEmpty()){
            System.out.println("etacarai");
            return new ResponseEntity<List<EmployeeMaster>>(searchFilters.getAllEmployeesWithQualifications(), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<EmployeeMaster>>(searchFilters.getEmployeesWithQualifications(qualificationsService.qualificationSearch(allParams)), HttpStatus.OK);
        }

    }

}
