package aimyamaguchi.co.jp.aimspringsql.qualifications;


import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class QualificationsService {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;


    public List<String> qualificationSearch(Map<String, String> map) {

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QQualificationsModel q = QQualificationsModel.qualificationsModel;

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId).from(e);
        filteredUsers.join(e.qualifications, q);

        map.entrySet().stream()
                .forEach(param -> {
                    switch (param.getKey()) {
                        case "sponsor":
                            filteredUsers.where(q.sponsor.eq(param.getValue()));
                            break;
                        case "qName":
                            filteredUsers.where(q.qName.eq(param.getValue()));
                            break;
                        case "examDate":
                            filteredUsers.where(q.examDate.eq(LocalDate.parse(param.getValue())));
                            break;
                        case "results":
                            filteredUsers.where(q.results.eq(param.getValue()));
                            break;
                        case "expenses":
                            switch(param.getValue().substring(0,2)) {
                                case "gt":
                                    filteredUsers.where(q.cost.goe(Integer.parseInt(param.getValue().substring(2))));
                                    break;
                                case "lt":
                                    filteredUsers.where(q.cost.loe(Integer.parseInt(param.getValue().substring(2))));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "id":
                            filteredUsers.where(e.shainId.eq(param.getValue()));
                            break;
                        case "name":
                            filteredUsers.where(e.shainName.contains(param.getValue()));
                            break;
                        case "kana":
                            filteredUsers.where(e.shainKana.contains(param.getValue()));
                            break;
                        case "retired":
                            if(param.getValue().equals("false")){
                                filteredUsers.where(e.shainRetired.isFalse());
                            }
                        break;
                    }
                });


        return filteredUsers.fetch();

    }


}
