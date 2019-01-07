package aimyamaguchi.co.jp.aimspringsql.controllers.curriculum;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumDAO;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvSearchService;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvDeleteService;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvInsertService;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins  = "*")
public class CurriculumController {

    @Autowired
    private CvInsertService cis;

    @Autowired
    private CvDeleteService cds;

    @Autowired
    private CvSearchService cs;

    @Autowired
    private SearchFilters search;

    @PostMapping("/shokureki/add")
    public ResponseEntity<String> insertcv(@RequestBody CurriculumDAO cv){

        try {
            cis.insertCV(cv);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/shokureki/delete")
    public ResponseEntity<String> deleteShokumu(@RequestParam(value="sid", required = true) Long sid){
    try {
        cds.softDeleteCV(sid);
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (CustomException e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shokureki/getall")
    public ResponseEntity<List<EmployeeMaster>> getAllEmployeeCVs(){
        return new ResponseEntity<>(search.getEmployeesWithCv(),HttpStatus.OK);
    }


    @GetMapping("/shokureki/search")
    public ResponseEntity<List<EmployeeMaster>> searchCv(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "n", required = false) String name,
            @RequestParam(value = "k", required = false) String kata,
            @RequestParam(value = "r", required = false) String recruit,
            @RequestParam(value = "age", required = false) String age,
            @RequestParam(value = "op", required = false) String operator,
            @RequestParam(value = "exp", required = false) String experience,
            @RequestParam(value = "idt", required = false) String indType,
            @RequestParam(value = "db", required = false) List<String> dbms,
            @RequestParam(value = "os", required = false) List<String> os,
            @RequestParam(value = "lng", required = false) List<String> lang,
            @RequestParam(value = "tls", required = false) List<String> tools,
            @RequestParam(value = "res", required = false) List<String> response,
            @RequestParam(value = "mkr", required = false) List<String> maker,
            @RequestParam(value = "cm", required = false) String customerName,
            @RequestParam(value = "tb", required = false) String targetBusiness
    ) {
        try {
            List<String> results = cs.searchForCV(id, name, kata, recruit, age, operator, experience, indType, dbms, os, lang, tools, response, maker, customerName, targetBusiness);
            return new ResponseEntity<>(search.getEmployeesWithCv(results), HttpStatus.OK);
        } catch (CustomException err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}