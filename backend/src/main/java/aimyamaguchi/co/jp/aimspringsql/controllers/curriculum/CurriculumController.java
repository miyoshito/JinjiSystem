package aimyamaguchi.co.jp.aimspringsql.controllers.curriculum;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumDAO;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvSearchService;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvDeleteService;
import aimyamaguchi.co.jp.aimspringsql.curriculum.services.CvInsertService;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.skillmap.SkillMap;
import aimyamaguchi.co.jp.aimspringsql.skillmap.SkillMapService;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/se")
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SkillMapService skillMapService;

    @PostMapping("/shokureki/add")
    public ResponseEntity<String> insertcv(@RequestBody CurriculumDAO cv){

        try {
            cis.insertCV(cv);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shokureki/search")
    public ResponseEntity<List<EmployeeMaster>> searchCvIn(@RequestParam Map<String, String> allParams, HttpServletRequest req){
        try {
            if
            (allParams.size() < 1
                    && jwtTokenProvider.isAdmin(jwtTokenProvider.resolveToken(req))
            ){
                return new ResponseEntity<>(search.getEmployeesWithCv(), HttpStatus.OK);
            } else {
                List<String> f = cs.getCvSearchResults(allParams,jwtTokenProvider.resolveToken(req));
                return new ResponseEntity<>(search.getEmployeesWithCv(f), HttpStatus.OK);
            }
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
            List<String> ids = skillMapService.skillMapSearchParams(id, name, katakana, affiliation);

            return new ResponseEntity<>(skillMapService.getSkillMap(ids, lang, os, dbms, tool, make, duty, inds), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}