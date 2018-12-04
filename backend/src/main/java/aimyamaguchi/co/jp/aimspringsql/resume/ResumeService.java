package aimyamaguchi.co.jp.aimspringsql.resume;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class ResumeService {

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

    public void saveResume(ResumeModel resume, HttpServletRequest req) {
        if (resume.getEmployee() == null || resume.getEmployee().equals("")) {
            return;
        }
        resume.setInsertDate(new Date());
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
            if (car.getCareerid() == null || car.getCareerid().equals("")){
                car.setActive(true);
                car.setK_resume(resume);
                career.save(car);
            } else {
                car.setK_resume(resume);
                career.save(car);
            }
        });
        resume.getQualifications().forEach(qual ->{
            if( qual.getQualificationid() == null || qual.getQualificationid().equals("")) {
                qual.setActive(true);
                qual.setS_resume(resume);
                qualification.save(qual);
            } else {
                qual.setS_resume(resume);
                qualification.save(qual);
            }
        });
        resume.getCommendations().forEach(com -> {
            if (com.getCommendationid() == null || com.getCommendationid().equals("")){
                com.setActive(true);
                com.setH_resume(resume);
                commendation.save(com);
            } else {
                com.setH_resume(resume);
                commendation.save(com);
            }
            });
        }

    public boolean deleteResumeDetails(Long id, String desc){

        if(desc.equals("career")) {
            System.out.println("Deleting career...");
            Career ca = career.findBycareerid(id);
            ca.setActive(false);
            career.save(ca);
            return true;
        } else if(desc.equals("qualification")){
            System.out.println("Deleting Qualification...");
            Qualification qu = qualification.findByqualificationid(id);
            qu.setActive(false);
            qualification.save(qu);
            return true;
        } else if(desc.equals("commendation")){
            System.out.println("Deleting Commendation...");
            Commendation co = commendation.findBycommendationid(id);
            co.setActive(false);
            commendation.save(co);
            return true;
        }
        else return false;

    }
    }
