package aimyamaguchi.co.jp.aimspringsql.resume;

import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private SearchFilters sf;

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
        System.out.println(resume.getEmployee().getShainId());
        if (resume.getEmployee().getShainId() == null || resume.getEmployee().getShainId().equals("")) {
            return;
        } else {
            ResumeModel res = sf.getResumeById(resume.getResumeId());
            res.setBunri(resume.getBunri());
            res.setUniversityName(resume.getUniversityName());
            res.setFormation(resume.getFormation());
            res.setCareers(resume.getCareers());
            res.setQualifications(resume.getQualifications());
            res.setCommendations(resume.getCommendations());
            res.setNotes(resume.getNotes());
            this.insertDetails(res);
            resumeRepo.save(res);
        }


        /*if (resume.getResumeId() == null || resume.getResumeId().equals("")) {
            resumeRepo.save(resume);
            ResumeModel res = resumeRepo.findByEmployee(employee.findByShainId(resume.getEmployee().getShainId()));
            res.setCareers(resume.getCareers());
            res.setQualifications(resume.getQualifications());
            res.setCommendations(resume.getCommendations());
            insertDetails(res);
        } else {
            insertDetails(resume);
            resumeRepo.save(resume);
        }*/
    }

    private void insertDetails(ResumeModel resume) {
        for (Career car : resume.getCareers()) {
            if (car.getCareerid() == null) {
                car.setActive(true);
                car.setK_resume(resume);
                career.save(car);
            } else {
                car.setK_resume(resume);
                career.save(car);
            }
        }
        for (Qualification qual : resume.getQualifications()) {
            if (qual.getQualificationid() == null) {
                qual.setActive(true);
                qual.setS_resume(resume);
                qualification.save(qual);
            } else {
                qual.setS_resume(resume);
                qualification.save(qual);
            }
        }
        for (Commendation com : resume.getCommendations()) {
            if (com.getCommendationid() == null) {
                com.setActive(true);
                com.setH_resume(resume);
                commendation.save(com);
            } else {
                com.setH_resume(resume);
                commendation.save(com);
            }
        }
    }

    public void deleteResumeDetails(String desc, Long id){
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

    public List<String> searchQueryBuilder(String id, String name, String kana, String recruit, String age, String school, String study, String bunri, String keireki, String shikaku){

        ArrayList<String> queryParam = new ArrayList<>();

        if (valid.isNullValidator(id)) queryParam.add("sha.sha_no = '"+id+"' and ");
        if (valid.isNullValidator(name)) queryParam.add("sha.sha_name like '%"+name+"%'");
        if (valid.isNullValidator(kana))queryParam.add("sha.sha_kana like '%"+kana+"%'");
        if (valid.isNullValidator(recruit))queryParam.add("sha.sha_recruit = '"+recruit+"' ");

        if (valid.isNullValidator(age)) {
            int start = Integer.parseInt(age);
            int end = start + 9;
            queryParam.add("year(getdate()) - year(sha.sha_birthday) between "+start+" and "+end);
        }
        if (valid.isNullValidator(study))queryParam.add("ri.ri_study_area like '%"+study+"%' ");
        if (valid.isNullValidator(bunri))queryParam.add("ri.ri_bunri like '%"+bunri+"%' ");
        if (valid.isNullValidator(keireki))queryParam.add("kei.rk_school_work like '%"+keireki+"%' or kei.rk_department_affiliation like '%"+keireki+"%'");



        if (valid.isNullValidator(shikaku))queryParam.add("shi.rs_qualification like '%"+shikaku+"%'");
        if (valid.isNullValidator(school)) queryParam.add("ri.ri_universityname like '%"+school+"%'");
        String param = String.join(" and\n", queryParam);



        Query query = entityManager.createNativeQuery(
                "SELECT DISTINCT sha.sha_no\n" +
                "from \n" +
                "m_shain sha join m_rirekisho ri on sha.sha_rireki = ri.ri_id\n" +
                "left join m_keireki kei on kei.rk_resume = ri.ri_id\n" +
                "left join m_shikaku shi on shi.rs_resume = ri.ri_id\n" +
                "left join m_hyosho hyo on hyo.rh_resume = ri.ri_id\n" +
                "where\n" + param);

        return query.getResultList();
    }




    }
