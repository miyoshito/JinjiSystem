package aimyamaguchi.co.jp.aimspringsql.controllers.views;

import java.util.List;
import java.util.Map;

import aimyamaguchi.co.jp.aimspringsql.constants.ConstantsViewBuilder;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvSearchService;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.AuthorizationService;
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
    private AuthorizationService es;

    @GetMapping("/public/cvparams")
    public Map<String,Object> getCvParams(){
        return builder.generateCvMap();
    }

    @GetMapping("/public/employee-params")
    public Map<String, Object> getEmpParams(){ return builder.getEmpMasterParams(); }

    @GetMapping("/public/industry-params")
    public List<INDUSTRYData>  getIndustryList() { return builder.getIndustryList(); }

/*    @GetMapping("/se/skillmapbuilder")
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

    }*/
}