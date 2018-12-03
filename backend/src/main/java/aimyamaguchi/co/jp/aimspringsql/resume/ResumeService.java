package aimyamaguchi.co.jp.aimspringsql.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepo;

    @Autowired
    private CareerRepository career;

    @Autowired
    private QualificationRepository qualification;

    @Autowired
    private CommendationRepository commendation;

    public void saveResume(ResumeModel resume, HttpServletRequest req) {

        System.out.println("USER => " +resume.getEmployee());
        System.out.println("RESUME => " +resume.getResumeId());

        if (resume.getEmployee() == null || resume.getEmployee().equals("")){
            return;
        }

        resume.setInsertDate(new Date());
            //resume.setInsertedBy(pegar o cara do token zzz);
        if(resume.getCareers() != null){
            resume.getCareers().forEach(value -> {
                value.setK_resume(resume);
                career.save(value);
            });
        }
        if(resume.getQualifications() != null){
            resume.getQualifications().forEach(value ->{
                value.setS_resume(resume);
                qualification.save(value);
            });
        }
        if(resume.getCommendations() != null) {
            resume.getCommendations().forEach(value -> {
                value.setH_resume(resume);
                commendation.save(value);
            });
        }
        resumeRepo.save(resume);
    }
}
