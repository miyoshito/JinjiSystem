package aimyamaguchi.co.jp.aimspringsql.controllers;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/getall")
    public List<DataModel> mkd(){
        List<DataModel> lista = new ArrayList<>();
        ci.getAll().forEach(value ->{
            System.out.println(value);
            lista.add(value);
        });
        return lista;
    }

    @GetMapping("/employee-dependencies")
    public List<EmployeeParamModel> getEmployeeMenus(){
        return epi.getAll();
    }
    
    
    

}