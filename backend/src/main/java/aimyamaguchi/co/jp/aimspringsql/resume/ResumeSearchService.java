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
import java.util.List;
import java.util.Map;

@Service
public class ResumeSearchService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getResumeSearchResults(Map<String, String> map){

        System.out.println(map);

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QResumeModel res = QResumeModel.resumeModel;
        QCommendation com = QCommendation.commendation;
        QQualification qlf = QQualification.qualification;
        QCareer crr = QCareer.career;
        QRecruitTypeModel qrt = QRecruitTypeModel.recruitTypeModel;

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
                            System.out.println("From ->" +f.getValue());
                            System.out.println("To -> " +f.getValue() + 9);
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
                    }
                });
        return filteredUsers.fetch();
    }




}
