package aimyamaguchi.co.jp.aimspringsql.resume;


import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QRecruitTypeModel;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResumeSearchService {

    @PersistenceContext
    private EntityManager entityManager;

    private QEmployeeMaster e = QEmployeeMaster.employeeMaster;
    private QResumeModel res = QResumeModel.resumeModel;
    private QCommendation com = QCommendation.commendation;
    private QQualification qlf = QQualification.qualification;
    private QCareer crr = QCareer.career;
    private QRecruitTypeModel qrt = QRecruitTypeModel.recruitTypeModel;

    public Map<String, List<String>> getResumeSearchParams(){

        Map<String, List<String>> map = new HashMap<>();

        JPAQuery<String> school = new JPAQueryFactory(entityManager).selectDistinct(res.universityName).from(res).where(res.universityName.isNotNull());
        JPAQuery<String> study = new JPAQueryFactory(entityManager).selectDistinct(res.formation).from(res).where(res.formation.isNotNull());

        map.put("school", school.fetch());
        map.put("study", study.fetch());

        return map;

    }
    public Map<String, List<String>> getResumeQualificationParams(){

        Map<String, List<String>> map = new HashMap<>();

        QQualification qqa = QQualification.qualification;

        JPAQuery<String> qualifications = new JPAQueryFactory(entityManager).selectDistinct(qqa.qualification_name).from(qqa).where(qqa.active.isTrue());

        map.put("qualifications", qualifications.fetch());

        return map;

    }




    public List<String> getResumeSearchResults(Map<String, String> map){

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId);

        filteredUsers
                .from(e)
                .join(e.resume, res);

        map.entrySet().stream()
                .forEach(f -> {
                    switch (f.getKey()) {
                        case "id":
                            filteredUsers.where(e.shainId.eq(f.getValue()));
                            break;
                        case "name":
                            filteredUsers.where(e.shainName.contains(f.getValue()));
                            break;
                        case "kana":
                            filteredUsers.where(e.shainKana.contains(f.getValue()));
                            break;
                        case "recruit":
                            filteredUsers.join(e.shainRecruit, qrt);
                            filteredUsers.where(qrt.id.eq(Long.valueOf(f.getValue())));
                            break;
                        case "age":
                            filteredUsers
                                    .where(DateTimeExpression.currentTimestamp().year()
                                            .subtract(e.shainBirthday.year())
                                            .between(Integer.parseInt(f.getValue()),
                                                     Integer.parseInt(f.getValue()) + 9));
                            break;
                        case "study":
                            filteredUsers.where(res.formation.contains(f.getValue()));
                            break;
                        case "school":
                            filteredUsers.where(res.universityName.contains(f.getValue()));
                            break;
                        case "bunri":
                            filteredUsers.where(res.bunri.eq(f.getValue()));
                            break;
                        case "career":
                            filteredUsers.leftJoin(res.careers, crr);
                            filteredUsers.where(crr.career_scwk.contains(f.getValue()).or(crr.career_dpaf.contains(f.getValue())));
                            break;
                        case "qualification":
                            filteredUsers.leftJoin(res.qualifications, qlf);
                            filteredUsers.where(qlf.qualification_name.contains(f.getValue()));
                            break;
                        case "retired":
                            if(f.getValue().equals("false")){
                                filteredUsers.where(e.shainRetired.isFalse());
                            }
                            break;
                        default: break;
                    }
                });
        return filteredUsers.fetch();
    }




}
