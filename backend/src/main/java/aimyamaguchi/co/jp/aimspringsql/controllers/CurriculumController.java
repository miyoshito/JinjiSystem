package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumDAO;
import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumService;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins  = "*")
public class CurriculumController {

    @Autowired
    private CurriculumService cs;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/shokureki/add")
    public ResponseEntity<String> insertcv(@RequestBody CurriculumDAO cv){

        try {
            cs.insertCV(cv);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/shokureki/delete")
    public ResponseEntity<String> deleteShokumu(@RequestParam(value="sid", required = true) Long sid){
    try {
        cs.softDeleteCV(sid);
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (CustomException e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        System.out.println(kata);
        try {
            List<String> results = cs.searchForCV(id, name, kata, recruit, age, operator, experience, indType, dbms, os, lang, tools, response, maker, customerName, targetBusiness);
            return new ResponseEntity<>(employeeRepository.findByShainIdIn(results), HttpStatus.OK);
        } catch (CustomException err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}