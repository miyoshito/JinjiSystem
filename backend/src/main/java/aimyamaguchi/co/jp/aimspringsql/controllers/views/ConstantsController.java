package aimyamaguchi.co.jp.aimspringsql.controllers.views;

import java.util.List;
import java.util.Map;

import aimyamaguchi.co.jp.aimspringsql.constants.ConstantsViewBuilder;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvSearchService;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.AuthorizationService;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins  = "*")
@RequestMapping("/api")
public class ConstantsController {

    @Autowired
    private ConstantsViewBuilder builder;

    @Autowired
    private CvSearchService cv;

    @Autowired
    private ResumeSearchService resumeSearchService;

    @Autowired
    private AuthorizationService es;

    @GetMapping("/public/cvparams")
    public Map<String,Object> getCvParams(){
        return builder.generateCvMap();
    }

    @GetMapping("/public/employee-params")
    public Map<String, Object> getEmpParams(){ return builder.getEmpMasterParams(); }

    @GetMapping("/public/industry-params")
    public List<INDUSTRYData>  getIndustryList() { return builder.getIndustryList(); }

    @GetMapping("/admin/rirekisearchlist")
    public Map<String, List<String>> getRirekishoSearchParams(){
        return resumeSearchService.getResumeSearchParams();
    }

    @GetMapping("/public/shokurirekisearchlist")
    public Map<String, List<String>> getShokuRirekiSearchParams(){
        return cv.getCvSearchParams();
    }

    @GetMapping("/public/shikakusearchlist")
    public Map<String, List<String>> getShikakuSearchParams(){
        return builder.getShikakuSearchableParams();
    }



}