package aimyamaguchi.co.jp.aimspringsql.resume;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.join;

@Service
public class ResumeService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ResumeRepository resumeRepo;

    @Autowired
    private EmployeeRepository employee;

    @Autowired
    private CareerRepository career;

    @Autowired
    private QualificationRepository qualification;

    @Autowired
    private CommendationRepository commendation;

    @Autowired
    private CustomValidators valid;

    public void saveResume(ResumeModel resume, HttpServletRequest req) {
        if (resume.getEmployee() == null || resume.getEmployee().equals("")) {
            return;
        }
        if (resume.getResumeId() == null || resume.getResumeId().equals("")) {
            resumeRepo.save(resume);
            ResumeModel res = resumeRepo.findByEmployee(employee.findByShainId(resume.getEmployee().getShainId()));
            res.setCareers(resume.getCareers());
            res.setQualifications(resume.getQualifications());
            res.setCommendations(resume.getCommendations());
            insertDetails(res);
        } else {
            insertDetails(resume);
            resumeRepo.saveAndFlush(resume);
        }
    }

    private void insertDetails(ResumeModel resume) {

        resume.getCareers().forEach((car) ->{
            if (car.getCareerid() == null){
                car.setActive(true);
                car.setK_resume(resume);
                career.save(car);
            } else {
                car.setK_resume(resume);
                career.save(car);
            }
        });
        resume.getQualifications().forEach(qual ->{
            if( qual.getQualificationid() == null) {
                qual.setActive(true);
                qual.setS_resume(resume);
                qualification.save(qual);
            } else {
                qual.setS_resume(resume);
                qualification.save(qual);
            }
        });
        resume.getCommendations().forEach(com -> {
            if (com.getCommendationid() == null){
                com.setActive(true);
                com.setH_resume(resume);
                commendation.save(com);
            } else {
                com.setH_resume(resume);
                commendation.save(com);
            }
            });
        }

    public void deleteResumeDetails(Long id, String desc){

        switch(desc){
            case "career":
                Career ca = career.findBycareerid(id);
                ca.setActive(false);
                career.save(ca);
                break;
            case "qualification":
                Qualification qu = qualification.findByqualificationid(id);
                qu.setActive(false);
                qualification.save(qu);
                break;
            case "commendation":
                Commendation co = commendation.findBycommendationid(id);
                co.setActive(false);
                commendation.save(co);
                break;
        }
    }

    public List<String> searchQueryBuilder(String id, String name, String kana, String recruit, String age, String study, String bunri, String keireki, String shikaku){

        ArrayList<String> queryParam = new ArrayList<>();
        if (valid.isNullValidator(id)) queryParam.add("sha.sha_no = '"+id+"' and ");
        Optional.ofNullable(name).ifPresent((p) -> {if (p != "") queryParam.add("sha.sha_name like '%"+p+"%' and");});
        Optional.ofNullable(kana).ifPresent((p) -> {if (p != "") queryParam.add("sha.sha_kana like '%"+p+"%' and");});
        Optional.ofNullable(recruit).ifPresent((p) -> {if (p != "") queryParam.add("sha.sha_recruit = '"+p+"' and");});
        Optional.ofNullable(age).ifPresent((p) -> {if (p != "") queryParam.add("year(getdate()) - year(sha.sha_birthday) = "+p+" and");});
        Optional.ofNullable(study).ifPresent((p) -> {if (p != "") queryParam.add("ri.ri_study_area like '%"+p+"%' and");});
        Optional.ofNullable(bunri).ifPresent((p) -> {if (p != "") queryParam.add("ri.ri_bunri like '%"+p+"%' and");});
        Optional.ofNullable(keireki).ifPresent((p) -> {if (p != "") queryParam.add("kei.rk_school_work like '%"+p+"%' and");});
        Optional.ofNullable(shikaku).ifPresent((p) -> {if (p != "") queryParam.add("shi.rs_qualification like '%"+p+"%' and");});


        String param = String.join(" ", queryParam);



        Query query = entityManager.createNativeQuery(
                "SELECT DISTINCT sha.sha_no\n" +
                "from \n" +
                "m_shain sha join m_rirekisho ri on sha.sha_no = ri.sha_no\n" +
                "left join m_keireki kei on kei.rk_resume = ri.ri_id\n" +
                "left join m_shikaku shi on shi.rs_resume = ri.ri_id\n" +
                "left join m_hyosho hyo on hyo.rh_resume = ri.ri_id\n" +
                "where\n" + param.substring(0, param.length() -4));

        return query.getResultList();
    }




    }
