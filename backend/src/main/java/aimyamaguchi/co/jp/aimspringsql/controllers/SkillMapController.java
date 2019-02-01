package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumDAO;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMin;
import aimyamaguchi.co.jp.aimspringsql.skillmap.SkillMapService;
import aimyamaguchi.co.jp.aimspringsql.skillmap.SkillMapUser;
import aimyamaguchi.co.jp.aimspringsql.skillmap.SkillMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/se")
@CrossOrigin(origins  = "*")
public class SkillMapController {

    @Autowired
    private SkillMapService skillMapService;


    @PostMapping("/skillmap/build")
    public List<SkillMapUser> insertcv(@RequestParam(value="id") String id){

        return skillMapService.skillMapSearchParams(id);
    }
}
