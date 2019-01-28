package aimyamaguchi.co.jp.aimspringsql.qualifications;


import aimyamaguchi.co.jp.aimspringsql.employee.Models.QEmployeeMaster;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        map.entrySet().stream()
                .forEach(param -> {
                    switch (param.getKey()) {

                    }
                });


        return filteredUsers.fetch();

    }


}
