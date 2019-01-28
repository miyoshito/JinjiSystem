package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.qualifications.QualificationsService;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<T> searchForQualifications(Map<String, String> allParams, HttpServletRequest req){
        if (allParams.isEmpty()){
            return new ResponseEntity<T>(searchFilters.getAllEmployeesWithQualifications());
        } else {
            return new ResponseEntity<T>(searchFilters.getEmployeesWithQualifications(qualificationsService.qualificationSearch(allParams)));
        }

    }

}
