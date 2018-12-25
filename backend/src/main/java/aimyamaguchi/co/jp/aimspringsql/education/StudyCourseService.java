package aimyamaguchi.co.jp.aimspringsql.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StudyCourseService {

    @Autowired
    StudyCourseInterface sci;


    public boolean insertSCAttempt(StudyCourseModel scm){
        LocalDate now = LocalDate.now();
                scm.setUpdated(now);
                sci.save(scm);
                return true;
    }

}
