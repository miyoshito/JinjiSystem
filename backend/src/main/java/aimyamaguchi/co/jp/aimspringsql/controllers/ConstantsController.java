package aimyamaguchi.co.jp.aimspringsql.controllers;

import java.util.List;
import java.util.Map;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumService;
import aimyamaguchi.co.jp.aimspringsql.curriculum.INDCLASSIFICATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins  = "*")
@RequestMapping("/api")
public class ConstantsController {

    @Autowired
    private CurriculumService cv;

    @Autowired
    private EmployeeService es;

    @GetMapping("/public/cv-params")
    public Map<String,Object> getCvParams(){
        return cv.generateCvMap();
    }

    @GetMapping("/public/employee-params")
    public Map<String, Object> getEmpParams(){ return es.getEmpMasterParams(); }

    @GetMapping("/public/industry-sublist")
    public List<INDCLASSIFICATIONData> getIndustryList() { return cv.industryList(); }

    
    
    

}