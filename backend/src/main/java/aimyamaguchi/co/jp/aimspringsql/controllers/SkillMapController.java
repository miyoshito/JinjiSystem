package aimyamaguchi.co.jp.aimspringsql.controllers;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumDAO;
import aimyamaguchi.co.jp.aimspringsql.skillmap.SkillMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/se")
@CrossOrigin(origins  = "*")
public class SkillMapController {

    @Autowired
    private SkillMapService skillMapService;


    @PostMapping("/skillmap/build")
    public void insertcv(@RequestParam(value="id") String id){

        skillMapService.skillMapSearchParams(id);

    }
}
