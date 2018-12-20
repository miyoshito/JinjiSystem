package aimyamaguchi.co.jp.aimspringsql.controllers;

import java.util.List;
import java.util.Map;

import aimyamaguchi.co.jp.aimspringsql.curriculum.*;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins  = "*")
@RequestMapping("/api")
public class ConstantsController {
    @Autowired
    private CurriculumService cv;

    @Autowired
    private EmployeeService es;

    @GetMapping("/public/cvparams")
    public Map<String,Object> getCvParams(){
        return cv.generateCvMap();
    }

    @GetMapping("/public/employee-params")
    public Map<String, Object> getEmpParams(){ return es.getEmpMasterParams(); }

    @GetMapping("/public/industry-params")
    public List<INDUSTRYData>  getIndustryList() { return cv.getIndustryList(); }


    @GetMapping("/public/theresnosense")
    public List<SkillMap> asdfffgaa(@RequestParam(value = "wtf", required = true) String id){
        return cv.fml(id);
    }
}