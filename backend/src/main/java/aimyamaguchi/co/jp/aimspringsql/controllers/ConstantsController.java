package aimyamaguchi.co.jp.aimspringsql.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.curriculum.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/se/skillmapbuilder")
    public ResponseEntity<List<SkillMap>> skillMapSearch(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "nm", required = false) String name,
            @RequestParam(value = "kt", required = false) String katakana,
            @RequestParam(value = "sh", required = false) Integer affiliation,
            @RequestParam(value = "lang", required = true) boolean lang,
            @RequestParam(value = "os", required = true) boolean os,
            @RequestParam(value = "dbms", required = true) boolean dbms,
            @RequestParam(value = "tool", required = true) boolean tool,
            @RequestParam(value = "make", required = true) boolean make,
            @RequestParam(value = "duty", required = true) boolean duty,
            @RequestParam(value = "ind", required = false) List<Integer> inds
    )
    {
        try {
            List<String> ids = cv.skillMapSearchParams(id, name, katakana, affiliation);




            return new ResponseEntity<>(cv.getSkillMap(ids, lang, os, dbms, tool, make, duty, inds), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}