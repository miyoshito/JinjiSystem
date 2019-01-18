package aimyamaguchi.co.jp.aimspringsql.controllers.employee;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMin;
import aimyamaguchi.co.jp.aimspringsql.employee.Services.EmployeeSearchFunctions;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins  = "*")
public class EmployeeGetController {

    @Autowired
    private SearchFilters sf;
    @Autowired
    private JwtTokenProvider jwtValidator;
    @Autowired
    private EmployeeSearchFunctions esr;


    @GetMapping("/admin/isregistered")
    public EmployeeMaster isRegistered(@RequestParam(value="id", required=true) String id){
        return sf.getEmployeeData(id);
    }

    @GetMapping("/admin/getemployeelist")
    public List<EmployeeMaster> getEmployeeList(){
        return sf.getEmployeesWithStudy();
    }

    @GetMapping("/se/data/{id}")
    public ResponseEntity<EmployeeMaster> getEmployeeData(
            HttpServletRequest req,
            @PathVariable String id,
            @RequestParam(value = "cv", required = false) boolean cv,
            @RequestParam(value = "rs", required = false) boolean resume,
            @RequestParam(value = "ed", required = false) boolean education)
    {
        /*
        considerando um cenario onde soh os soumu/admins podem acessar a tela de insert e buscarem pelo ID...
         */
        try {
            jwtValidator.validateToken(jwtValidator.resolveToken(req));
            EmployeeMaster emp = sf.getEmployeeData(id);
            //as 3 funcoes servem soh pra eu forcar o load, ja que os 2 sao lazy.
            if (cv) emp.getCurriculum().size();
            if (education) emp.getEducations().size();
            if (resume && jwtValidator.getAreas(jwtValidator.resolveToken(req)).contains(3)) {
                sf.getResumeById(emp.getResume().getResumeId());
            }
            return new ResponseEntity<>(sf.getEmployeeData(id), HttpStatus.OK);
        } catch (AuthenticationException e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/se/getmyinfos")
    public ResponseEntity<EmployeeMin> getLoggedUserInfo(HttpServletRequest req){
        //essa funcao tanto valida o token de quem fez o request quanto ja transforma o return em algo menor.
            EmployeeMaster e = sf.getEmployeeByToken(req);
            if(e == null){
                return null;
            } else {
                EmployeeMin em = new EmployeeMin();
                em.setId(e.getShainId());
                em.setFullName(e.getShainName());
                //String affiliation = String.join("/", e.getAffiliation().stream().map(AFFILIATIONData::getDesc).collect(Collectors.toList()));
                em.setGroup(e.getAffiliation());
                em.setAdmin(e.isAdmin());
                return new ResponseEntity<>(em, HttpStatus.OK);
            }
    }

    @GetMapping("/admin/getprofile/{id}")
    public ResponseEntity<EmployeeMaster> getProfile(
            @PathVariable String id,
            @RequestParam(value = "cv", required = false) boolean cv,
            @RequestParam(value = "rs", required = false) boolean resume,
            @RequestParam(value = "ed", required = false) boolean education,
            HttpServletRequest req
    ){
        try {
            EmployeeMaster e = sf.getEmployeeData(id);
            if (resume && jwtValidator.isAdmin(jwtValidator.resolveToken(req))) {
                sf.getResumeById(e.getResume().getResumeId());
            }
            if (cv) {
                e.getCurriculum().size();
            }
            if (education) {
                e.getEducations().size();
            }
            return new ResponseEntity<>(e, HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/admin/search-employee")
    public ResponseEntity<List<EmployeeMaster>> searchEmployee(@RequestParam Map<String, String> allParameters){
        try {
            if (allParameters.size() < 1){
                return new ResponseEntity<>(sf.getAllEmployees(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(sf.getEmployeesIn(esr.searchResults(allParameters)), HttpStatus.OK);
            }
        } catch (CustomException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
