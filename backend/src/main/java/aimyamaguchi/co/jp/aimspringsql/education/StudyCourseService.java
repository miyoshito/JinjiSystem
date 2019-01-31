package aimyamaguchi.co.jp.aimspringsql.education;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyCourseService {

    @Autowired
    private StudyCourseInterface sci;

    @Autowired
    private CustomValidators valid;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SearchFilters sf;

    @Autowired
    JwtTokenProvider jwt;



    public boolean insertSCAttempt(StudyCourseModel scm, HttpServletRequest req){
                scm.setEmployee(sf.getEmployeeData(scm.getEmployee_id()));
                sci.save(scm);
                return true;
    }

    public List<String> getSCSponsorList(){
        return sci.findAll().stream()
                .map(StudyCourseModel::getSponsor)
                .collect(Collectors.toList());
    }
    public List<String> getSCEduNameList(){
        return sci.findAll().stream()
                .map(StudyCourseModel::getEducationName)
                .collect(Collectors.toList());
    }

    public boolean softDeleteSC(Long id){
        if(sf.getStudyCourseById(id) != null){
            StudyCourseModel scm = sf.getStudyCourseById(id);
            scm.setActive(false);
            sci.save(scm);
            return true;
        } else return false;
    }

    public List<StudyCourseModel> StudyCourseSearchResultsv2(Map<String, String> map){

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QStudyCourseModel qscm = QStudyCourseModel.studyCourseModel;

        JPAQuery<StudyCourseModel> filteredUsers = new JPAQueryFactory(entityManager).select(qscm).from(qscm);
        filteredUsers.join(qscm.employee, e);

        List<StudyCourseModel> finalList = new ArrayList<>();

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
                        case "sponsor":
                            filteredUsers.where(qscm.sponsor.eq(f.getValue()));
                            break;
                        case "educationName":
                            filteredUsers.where(qscm.educationName.eq(f.getValue()));
                            break;
                        case "expenses":
                            switch(f.getValue().substring(0,2)) {
                                case "gt":
                                    filteredUsers
                                            .where(
                                                    qscm.hotelExpenses
                                                            .add(qscm.tuitionFee)
                                                            .add(qscm.transportExpenses)
                                                            .goe(Integer.parseInt(f.getValue().substring(2))));
                                    break;
                                case "lt":
                                    filteredUsers
                                            .where(
                                                    qscm.hotelExpenses
                                                            .add(qscm.tuitionFee)
                                                            .add(qscm.transportExpenses)
                                                            .loe(Integer.parseInt(f.getValue().substring(2))));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "stdate":
                            LocalDate from = LocalDate.parse(f.getValue());
                            filteredUsers.where(qscm.startPeriod.goe(from));
                            break;
                        case "enddate":
                            LocalDate to = LocalDate.parse(f.getValue());
                            filteredUsers.where(qscm.endPeriod.loe(to));
                            break;
                        case "retired":
                            if(f.getValue().equals("false")){
                                filteredUsers.where(e.shainRetired.isFalse());
                            }
                            break;
                    }
                });

                filteredUsers.fetch().stream().forEach(r ->{
                    r.setEmployee_id(Optional.of(r.getEmployee().getShainId()).orElse(null));
                    r.setEmployee_name(r.getEmployee().getShainName());
                    r.setShainRetired(r.getEmployee().isShainRetired());
                    finalList.add(r);
                });

                return finalList;

    }

    @Deprecated
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
                            filteredUsers.where(e.shainId.eq(f.getValue()));
                            break;
                        case "name":
                            filteredUsers.where(e.shainName.contains(f.getValue()));
                            break;
                        case "kana":
                            filteredUsers.where(e.shainKana.contains(f.getValue()));
                            break;
                        case "sponsor":
                            filteredUsers.where(qscm.sponsor.eq(f.getValue()));
                            break;
                        case "educationName":
                            filteredUsers.where(qscm.educationName.eq(f.getValue()));
                            break;
                        case "expenses":
                            switch(f.getValue().substring(0,2)) {
                                case "gt":
                                filteredUsers
                                .where(
                                qscm.hotelExpenses
                                .add(qscm.tuitionFee)
                                .add(qscm.transportExpenses)
                                .goe(Integer.parseInt(f.getValue().substring(2))));
                                break;
                                case "lt":
                                    filteredUsers
                                .where(
                                qscm.hotelExpenses
                                .add(qscm.tuitionFee)
                                .add(qscm.transportExpenses)
                                .loe(Integer.parseInt(f.getValue().substring(2))));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "stdate":
                            LocalDate from = LocalDate.parse(f.getValue());
                            filteredUsers.where(qscm.startPeriod.goe(from));
                            break;
                        case "enddate":
                            LocalDate to = LocalDate.parse(f.getValue());
                            filteredUsers.where(qscm.endPeriod.loe(to));
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

    public Optional<StudyCourseModel> findSCM(String id){
        return sci.findById(Long.valueOf(id));
    }

}
