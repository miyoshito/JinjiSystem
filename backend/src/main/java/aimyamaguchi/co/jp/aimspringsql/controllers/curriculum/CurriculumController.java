package aimyamaguchi.co.jp.aimspringsql.controllers.curriculum;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
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
                    && jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req)).equals("ADMIN")
                    || jwtTokenProvider.getRole(jwtTokenProvider.resolveToken(req)).equals("SOUMU")
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

}