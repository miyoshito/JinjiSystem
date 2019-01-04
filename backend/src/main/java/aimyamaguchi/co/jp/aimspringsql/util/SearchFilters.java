package aimyamaguchi.co.jp.aimspringsql.util;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.CurriculumRepository;
import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseInterface;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SearchFilters {

    //wiring all repositories
    @Autowired
    private EmployeeRepository er;

    @Autowired
    private CurriculumRepository cr;

    @Autowired
    private ResumeRepository rr;

    @Autowired
    private StudyCourseInterface sr;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public EmployeeMaster getEmployeeData(String id){
        EmployeeMaster e = er.findByShainId(id);
        if (e != null) {
            System.out.println("Eita carai");
            return e;
        } else
            return null;
    }

    public List<EmployeeMaster> getEmployeesIn(List<String> ids) {
        List<EmployeeMaster> e = er.findByShainIdIn(ids);
        if (e.size() > 0) {
            return e;
        } else
            return null;
    }

    public EmployeeMaster getEmployeeWithCv(String id){
        EmployeeMaster e = er.findByShainId(id);
        if (e != null) {
            e.getCurriculum().size();
            return e;
        } else
            return null;
    }

    public EmployeeMaster getEmployeeWithResume(String id){
        EmployeeMaster e = er.findByShainId(id);
        if (e != null) {

            rr.findById(e.getResume().getResumeId());
            return e;
        } else
            return null;
    }

    public List<EmployeeMaster> getEmployeesWithCv(List<String> ids) {
        List<EmployeeMaster> le = er.findByShainIdIn(ids);
        for(EmployeeMaster e: le){
            e.getCurriculum().size();
        }
        return le;
    }

    public List<EmployeeMaster> getEmployeesWithResume(List<String> ids) {
        List<EmployeeMaster> le = er.findByShainIdIn(ids);
        for(EmployeeMaster e: le){
            rr.findById(e.getResume().getResumeId());
        }
        return le;
    }

    public EmployeeMaster getEmployeeFullData(String id){
        EmployeeMaster e = er.findByShainId(id);
        e.getCurriculum().size();
        e.getEducations().size();
        rr.findById(e.getResume().getResumeId());
        return e;
    }

    public EmployeeMaster getEmployeeByToken(HttpServletRequest req){
        String userid = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        if (!userid.equals("")) {
            return er.findByShainId(userid);
        } else return null;
    }

    public ResumeModel getResumeById(Long id){
        return rr.findById(id).orElse(null);
    }











}
