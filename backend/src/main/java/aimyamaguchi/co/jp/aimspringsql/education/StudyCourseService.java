package aimyamaguchi.co.jp.aimspringsql.education;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudyCourseService {

    @Autowired
    private StudyCourseInterface sci;

    @Autowired
    private CustomValidators valid;

    @PersistenceContext
    private EntityManager entityManager;



    public boolean insertSCAttempt(StudyCourseModel scm){
        LocalDate now = LocalDate.now();
                scm.setUpdated(now);
                sci.save(scm);
                return true;
    }

    public List<String> StudyCourseSearchResults(Map<String, String> map){

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QStudyCourseModel qscm = QStudyCourseModel.studyCourseModel;

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId);
        filteredUsers
                .from(e)
                .leftJoin(e.educations,qscm);

        map.entrySet().stream()
                .forEach(f -> {
                    switch (f.getKey()) {
                        case "id":
                            System.out.println(f.getValue());
                            filteredUsers.where(e.shainId.eq(f.getValue()));
                            break;
                        case "name":
                            filteredUsers.where(e.shainName.contains(f.getValue()));
                            break;
                        case "kana":
                            filteredUsers.where(e.shainKana.contains(f.getValue()));
                            break;
                        case "sponsor":
                            filteredUsers.where(qscm.sponsor.contains(f.getValue()));
                        case "expenses":
                            switch(f.getValue().substring(0,2)) {
                                case "gt":
                                filteredUsers
                                .where(
                                qscm.hotelExpenses
                                .add(qscm.tuitionFee)
                                .add(qscm.transportExpenses)
                                .as("total").goe(Integer.parseInt(f.getValue().substring(2))));
                                break;
                                case "lt":
                                    filteredUsers
                                .where(
                                qscm.hotelExpenses
                                .add(qscm.tuitionFee)
                                .add(qscm.transportExpenses)
                                .as("total").loe(Integer.parseInt(f.getValue().substring(2))));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "sd":
                            LocalDate from = LocalDate.parse(f.getValue());
                            filteredUsers.where(qscm.startPeriod.after(from));
                            break;
                        case "ed":
                            LocalDate to = LocalDate.parse(f.getValue());
                            filteredUsers.where(qscm.endPeriod.before(to));

                    }
                });

        return filteredUsers.fetch();
    }

    public Optional<StudyCourseModel> findSCM(String id){
        return sci.findById(Long.valueOf(id));
    }

}
