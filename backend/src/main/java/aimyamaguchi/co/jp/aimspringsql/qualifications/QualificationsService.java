package aimyamaguchi.co.jp.aimspringsql.qualifications;


import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QualificationsService {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SearchFilters searchFilters;

    @Autowired
    private QualificationsRepository qualificationsRepository;

    public List<QualificationsModel> qualificationSearchv2(Map<String, String> map){

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QQualificationsModel q = QQualificationsModel.qualificationsModel;

        JPAQuery<QualificationsModel> query = new JPAQueryFactory(entityManager).select(q).from(q);

        List<QualificationsModel> finalList = new ArrayList<>();

        query.leftJoin(q.employee, e);

        map.entrySet().stream()
                .forEach(param -> {
                    switch (param.getKey()) {
                        case "sponsor":
                            query.where(q.sponsor.eq(param.getValue()));
                            break;
                        case "qName":
                            query.where(q.qName.eq(param.getValue()));
                            break;
                        case "examDate":
                            query.where(q.examDate.eq(LocalDate.parse(param.getValue())));
                            break;
                        case "results":
                            query.where(q.results.eq(param.getValue()));
                            break;
                        case "expenses":
                            switch(param.getValue().substring(0,2)) {
                                case "gt":
                                    query.where(q.examFee.goe(Integer.parseInt(param.getValue().substring(2))));
                                    break;
                                case "lt":
                                    query.where(q.examFee.loe(Integer.parseInt(param.getValue().substring(2))));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "id":
                            query.where(e.shainId.eq(param.getValue()));
                            break;
                        case "name":
                            query.where(e.shainName.contains(param.getValue()));
                            break;
                        case "kana":
                            query.where(e.shainKana.contains(param.getValue()));
                            break;
                        case "retired":
                            if(param.getValue().equals("false")){
                                query.where(e.shainRetired.isFalse());
                            }
                            break;
                    }
                });

        query.fetch().stream()
                .forEach(r -> {
                    r.setEmployee_id(r.getEmployee().getShainId());
                    r.setEmployee_name(r.getEmployee().getShainName());
                    r.setShainRetired(r.getEmployee().isShainRetired());
                    finalList.add(r);
                });

        return finalList;
    }


    public List<String> qualificationSearch(Map<String, String> map) {

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QQualificationsModel q = QQualificationsModel.qualificationsModel;

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId).from(e);
        filteredUsers.leftJoin(e.qualifications, q);

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
                                    filteredUsers.where(q.examFee.goe(Integer.parseInt(param.getValue().substring(2))));
                                    break;
                                case "lt":
                                    filteredUsers.where(q.examFee.loe(Integer.parseInt(param.getValue().substring(2))));
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


    public boolean insertAttempt(QualificationsModel q, HttpServletRequest req){
            q.setEmployee(searchFilters.getEmployeeData(q.getEmployee_id()));
            qualificationsRepository.save(q);
            return true;
    }
}
