package aimyamaguchi.co.jp.aimspringsql.curriculum.services;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.*;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Period;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class CvSearchService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private IndustryDataRepository industryr;
    @Autowired
    private EmployeeRepository err;
    @Autowired
    private CurriculumRepository cir;
    @Autowired
    private CustomValidators validator;

    public List<String> getCvSearchResults(Map<String, String> map) {

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QCurriculumModel c = QCurriculumModel.curriculumModel;
        QINDUSTRYData indt = QINDUSTRYData.iNDUSTRYData;        //tem a list<>
        QINDCLASSIFICATIONData indc = QINDCLASSIFICATIONData.iNDCLASSIFICATIONData; //tem a ck

        QDBMSData dbms = QDBMSData.dBMSData;
        QOSData os = QOSData.oSData;
        QASSIGNData assign = QASSIGNData.aSSIGNData;
        QDUTYData duty = QDUTYData.dUTYData;
        QLANGData lang = QLANGData.lANGData;
        QMAKERData maker = QMAKERData.mAKERData;
        QTOOLSData tools = QTOOLSData.tOOLSData;


        String operator = "=";
        Integer experience = 0;

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId);
        BooleanBuilder b = new BooleanBuilder();
        filteredUsers
                .from(e)
                .join(e.curriculum, c)
                .join(c.industryClassData, indc)
                .join(indc.id.industryid, indt);


        map.entrySet().stream()
                .forEach(f -> {
                    switch (f.getKey()){
                        case "id":
                            System.out.println(f.getValue());
                            filteredUsers.where(e.shainId.eq(f.getValue()));
                            break;
                        case "n":
                            filteredUsers.where(e.shainName.contains(f.getValue()));
                            break;
                        case "k":
                            filteredUsers.where(e.shainKana.contains(f.getValue()));
                            break;
                        case "r":
                            filteredUsers.where(e.shainRecruit.contains(f.getValue()));
                            break;
                        case "age":
                            filteredUsers
                                    .where(DateTimeExpression.currentTimestamp().year()
                                            .subtract(e.shainBirthday.year())
                                            .between(Integer.parseInt(f.getValue()),
                                                     Integer.parseInt(f.getValue() + 9)));
                            break;
                        case "experience":
                            switch (f.getValue().substring(0, 2)) {
                                case "eq":
                                    break;
                                case "gt":
                                    filteredUsers.where(e.totalExperienceTime.goe(Integer.parseInt(f.getValue().substring(2))));
                                    break;
                                case "lt":
                                    filteredUsers.where(e.totalExperienceTime.loe(Integer.parseInt(f.getValue().substring(2))));
                                    break;
                            }
                            break;
                        case "idt":
                            filteredUsers.where(c.industryClassData.id.industryid.id.eq(Long.valueOf(f.getValue())));
                            break;
                        case "cm":
                            filteredUsers.where(c.customer.contains(f.getValue()));
                            break;
                        case "tb":
                            filteredUsers.where(c.targetbusiness.contains(f.getValue()));
                            break;
                            // Array cases ~
                        case "db":
                            filteredUsers.join(c.dbmsData, dbms);
                            Collection<Long> cdb = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(dbms.id.in(cdb));
                            break;
                        case "os":
                            filteredUsers.join(c.osData, os);
                            Collection<Long> cos = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(os.id.in(cos));
                            break;
                        case "as":
                            filteredUsers.join(c.assignData, assign);
                            Collection<Long> cas = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(assign.id.in(cas));
                            break;
                        case "dt":
                            filteredUsers.join(c.responseData, duty);
                            Collection<Long> cdt = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(duty.id.in(cdt));
                            break;
                        case "lng":
                            filteredUsers.join(c.langData, lang);
                            Collection<Long> clg = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(lang.id.in(clg));
                            break;
                        case "mk":
                            filteredUsers.join(c.makerData, maker);
                            Collection<Long> cmk = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(maker.id.in(cmk));
                            break;
                        case "tl":
                            filteredUsers.join(c.toolsData, tools);
                            Collection<Long> ctl = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(tools.id.in(ctl));
                            break;
                    }
                });
        return filteredUsers.fetch();
    }

}
