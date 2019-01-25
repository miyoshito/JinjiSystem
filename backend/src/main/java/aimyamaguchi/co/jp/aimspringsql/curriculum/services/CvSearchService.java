package aimyamaguchi.co.jp.aimspringsql.curriculum.services;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.*;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.*;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private QEmployeeMaster e = QEmployeeMaster.employeeMaster;
    private QRecruitTypeModel qrt = QRecruitTypeModel.recruitTypeModel;
    private QCurriculumModel c = QCurriculumModel.curriculumModel;
    private QINDUSTRYData indt = QINDUSTRYData.iNDUSTRYData;        //tem a list<>
    private QINDCLASSIFICATIONData indc = QINDCLASSIFICATIONData.iNDCLASSIFICATIONData; //tem a ck

    private QDBMSData dbms = QDBMSData.dBMSData;
    private QOSData os = QOSData.oSData;
    private QASSIGNData assign = QASSIGNData.aSSIGNData;
    private QDUTYData duty = QDUTYData.dUTYData;
    private QLANGData lang = QLANGData.lANGData;
    private QMAKERData maker = QMAKERData.mAKERData;
    private QTOOLSData tools = QTOOLSData.tOOLSData;

    public Map<String, List<String>> getCvSearchParams(){

        Map<String, List<String>> map = new HashMap<>();

        JPAQuery<String> customers = new JPAQueryFactory(entityManager).select(c.customer).from(c);
        JPAQuery<String> business = new JPAQueryFactory(entityManager).select(c.targetbusiness).from(c);

        map.put("customers", customers.fetch());
        map.put("business", business.fetch());

        return map;

    }

    public List<String> getCvSearchResults(Map<String, String> map, String token) {





        String operator = "=";
        Integer experience = 0;

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId);
        filteredUsers
                .from(e)
                .leftJoin(e.curriculum, c);


        QAFFILIATIONData af = QAFFILIATIONData.aFFILIATIONData;
        QPOSITIONData pos = QPOSITIONData.pOSITIONData;
        List<Long> groups = new ArrayList<>();

        jwtTokenProvider.getAreas(token).stream().forEach(ar ->{
            groups.add(Long.valueOf(ar));
        });

        if(!jwtTokenProvider.isAdmin(token)){
            filteredUsers.where(e.shainDeletedFlag.isFalse())
                    .join(e.affiliation,af)
                    .join(e.position,pos)
                    .where(af.id.in(groups))
                    .where(pos.id.goe(jwtTokenProvider.getAuthority(token)))
                    .where(pos.id.loe(900000));
        }


        map.entrySet().stream()
                .forEach(f -> {
                    switch (f.getKey()){
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
                            filteredUsers.leftJoin(e.shainRecruit, qrt);
                            filteredUsers.where(qrt.id.eq(Long.valueOf(f.getValue())));
                            break;
                        case "age":
                            filteredUsers
                                    .where(DateTimeExpression.currentTimestamp().year()
                                            .subtract(e.shainBirthday.year())
                                            .between(Integer.parseInt(f.getValue()),
                                                     Integer.parseInt(f.getValue()) + 9));
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
                        case "indType":
                            filteredUsers
                                .leftJoin(c.industryClassData, indc)
                                .leftJoin(indc.id.industryid, indt);
                            filteredUsers.where(c.industryClassData.id.industryid.id.eq(Long.valueOf(f.getValue())));
                            break;
                        case "customerName":
                            filteredUsers.where(c.customer.contains(f.getValue()));
                            break;
                        case "targetBusiness":
                            filteredUsers.where(c.targetbusiness.contains(f.getValue()));
                            break;
                            // Array cases ~
                        case "dbms":
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
                        case "role":
                            filteredUsers.join(c.assignData, assign);
                            Collection<Long> cas = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(assign.id.in(cas));
                            break;
                        case "response":
                            filteredUsers.join(c.responseData, duty);
                            filteredUsers.where(duty.id.eq(Long.valueOf(f.getValue())));
                            break;
                        case "lang":
                            filteredUsers.join(c.langData, lang);
                            Collection<Long> clg = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(lang.id.in(clg));
                            break;
                        case "maker":
                            filteredUsers.join(c.makerData, maker);
                            Collection<Long> cmk = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(maker.id.in(cmk));
                            break;
                        case "tools":
                            filteredUsers.join(c.toolsData, tools);
                            Collection<Long> ctl = Stream.of(f.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(tools.id.in(ctl));
                            break;
                        case "retired":
                            if(f.getValue().equals("false")){
                                filteredUsers.where(e.shainRetired.isFalse());
                            }
                            break;

                    }
                });
        return filteredUsers.fetch();
    }

}
