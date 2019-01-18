package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QAFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QPOSITIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeSearchFunctions {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<String> searchResults(Map<String, String> map) {

        QEmployeeMaster e = QEmployeeMaster.employeeMaster;
        QAFFILIATIONData q = QAFFILIATIONData.aFFILIATIONData;

        JPAQuery<String> filteredUsers = new JPAQueryFactory(entityManager).selectDistinct(e.shainId).from(e);

        map.entrySet().stream()
                .forEach(param -> {
                    switch (param.getKey()) {
                        case "id":
                            filteredUsers.where(e.shainId.eq(param.getValue()));
                            break;
                        case "name":
                            filteredUsers.where(e.shainName.contains(param.getValue()));
                            break;
                        case "kana":
                            filteredUsers.where(e.shainKana.contains(param.getValue()));
                            break;
                        case "affiliation":
                            filteredUsers.leftJoin(e.affiliation, q);
                            Collection<Long> aff = Stream.of(param.getValue().split(","))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            filteredUsers.where(q.id.in(aff));
                            break;
                        default:
                            break;
                    }

                });

        return filteredUsers.fetch();
    }




}
