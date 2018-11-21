package aimyamaguchi.co.jp.aimspringsql.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumService;
import aimyamaguchi.co.jp.aimspringsql.curriculum.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.employee.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aimyamaguchi.co.jp.aimspringsql.constants.DataInterface;
import aimyamaguchi.co.jp.aimspringsql.constants.DataModel;
import aimyamaguchi.co.jp.aimspringsql.constants.EmployeeParamInterface;
import aimyamaguchi.co.jp.aimspringsql.constants.EmployeeParamModel;

@RestController
@CrossOrigin(origins  = "*")
@RequestMapping("/api")
public class ConstantsController {

    @Autowired
    private DataInterface ci;

    @Autowired
    private EmployeeParamInterface epi;

    @Autowired
    private AffiliationRepository ar;

    @Autowired
    private CurriculumService cv;

    @GetMapping("/public/cv-params")
    public Map<String,Object> getCvParams(){
        return cv.generateCvMap();
    }

    @GetMapping("/public/employee-dependencies")
    public List<EmployeeParamModel> getEmployeeMenus(){
        return epi.getAll();
    }

    @GetMapping("/public/shozoku-list")
    public List<AFFILIATIONData> getShozokuList(){ return ar.findAll();}

    @GetMapping("/public/industry-list")
    public List<INDUSTRYData> getIndustryList(){
        return cv.industryList();
    }
    
    
    

}