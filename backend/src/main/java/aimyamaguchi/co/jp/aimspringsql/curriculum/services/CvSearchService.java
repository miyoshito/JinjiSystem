package aimyamaguchi.co.jp.aimspringsql.curriculum.services;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.*;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;


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



    public List<String> searchForCV(
            String id,
            String name,
            String kana,
            String recruit,
            String age,
            String operator,
            String experience,
            String indType,
            List<String> dbms,
            List<String> os,
            List<String> lang,
            List<String> tools,
            List<String> response,
            List<String> maker,
            String customerName,
            String targetBusiness
    ) {
        ArrayList<String> queryJoins = new ArrayList<>();
        ArrayList<String> queryParam = new ArrayList<>();


        if (validator.isNullValidator(id)) queryParam.add("and m.sha_no = '" + id + "'\n");
        if (validator.isNullValidator(name)) queryParam.add("and m.sha_name like '%" + name + "%'\n");
        if (validator.isNullValidator(kana)) queryParam.add("and m.sha_kana like '%" + kana + "%'\n");
        if (validator.isNullValidator(recruit)) queryParam.add("and m.sha_recruit = '" + recruit + "'\n");
        if (validator.isNullValidator(age)) queryParam.add("and year(getdate()) - year(m.sha_birthday) = " + age + "\n");
        if (validator.isNullValidator(customerName)) queryParam.add("and sho.cv_customer like '%" + customerName + "%'\n");
        if (validator.isNullValidator(targetBusiness)) queryParam.add("and sho.cv_targetbusiness like '%" + targetBusiness + "%'\n");

        if (validator.isNullValidator(experience) && validator.isNullValidator(operator)) {
            switch (operator) {
                case "equals":
                    queryParam.add("and datediff(year, sho.cv_start, sho.cv_end) = " + experience);
                    break;
                case "greaterthan":
                    queryParam.add("and datediff(year, sho.cv_start, sho.cv_end) >= " + experience);
                    break;
                case "lessthan":
                    queryParam.add("and datediff(year, sho.cv_start, sho.cv_end) <= " + experience);
                    break;
            }

        }
        if (validator.isNullValidator(indType)) queryParam.add("and gyo.industry_type_desc like '%" + indType + "%'\n");

        if ((dbms != null) && (dbms.size() > 0)) {
            String dbmsin = String.join(",", dbms);
            queryParam.add("and ddbms.dbms_id in(" + dbmsin + ")");

            queryJoins.add("\n" +
                    "left outer join cv_dbms dbms on dbms.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_dbms ddbms on ddbms.dbms_id = dbms.dbms_data_dbms_id");
        }
        if ((os != null) && (os.size() > 0)) {
            String osin = String.join(",", os);
            queryParam.add("and dos.os_id in (" + osin + ")");
            queryJoins.add("\n" +
                    "left outer join cv_os os on os.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_os dos on dos.os_id = os.os_data_os_id");
        }

        if (lang != null && lang.size() > 0) {
            String langin = String.join(",", lang);
            queryParam.add("and dlang.lang_id in (" + langin + ")");

            queryJoins.add("\n" +
                    "left outer join cv_lang lang on lang.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_lang dlang on dlang.lang_id = lang.lang_data_lang_id");
        }

        if (tools != null && tools.size() > 0) {
            String toolsin = String.join(",", tools);
            queryParam.add("and dtools.tools_id in (" + toolsin + ")");

            queryJoins.add("\n" +
                    "left outer join cv_tools tools on tools.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_tools dtools on dtools.tools_id = tools.tools_data_tools_id");
        }
        if (response != null && response.size() > 0) {
            String responsein = String.join(",", response);
            queryParam.add("and dresponse.response_id in (" + responsein + ")");

            queryJoins.add("\n" +
                    "left outer join cv_response response on response.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_response dresponse on dresponse.response_id = response.response_data_response_id");
        }

        if (maker != null && maker.size() > 0) {
            String makerin = String.join(",", maker);
            queryParam.add("and dmaker.maker_id in (" + makerin + ")");

            queryJoins.add("\n" +
                    "left outer join cv_maker maker on maker.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_maker dmaker on dmaker.maker_id = maker.maker_data_maker_id");
        }

        String param = String.join("\n", queryParam);
        String joins = String.join("\n", queryJoins);
        Query query = entityManager.createNativeQuery(
                "select DISTINCT m.sha_no\n"
                        + "from\n"
                        + "m_shain m join m_shokumukeireki sho on m.sha_no = sho.sha_no\n"
                        + "left outer join m_gyoumu gyo  on gyo.industry_type_id = sho.cv_industry_type\n"
                        + "left outer join m_gykubun gyk on sho.cv_industry_class = gyk.industry_class_id\n"
                        + joins
                        + "\nwhere\n"
                        + "gyk.industry_type_id = gyo.industry_type_id\n"
                        + param);

        if(query.getResultList().size() < 1) {
            ArrayList<String> subQuery = new ArrayList<>();
            if(validator.isNullValidator(id)) subQuery.add("m.sha_no ="+id);
            if(validator.isNullValidator(name)) subQuery.add("m.sha_name like '%"+name+"%'");
            if(validator.isNullValidator(kana)) subQuery.add("m.sha_kana like '%"+kana+"%'");
            if(validator.isNullValidator(recruit)) subQuery.add("m.sha_recruit = '"+recruit+"'");

            String p2 = String.join("\n or ",subQuery);

            if (subQuery.size() > 0){
                Query q2 = entityManager.createNativeQuery("select distinct m.sha_no from m_shain m where "+p2);
                return q2.getResultList();
            }
        } else {return query.getResultList();}
        return new ArrayList<>();
    }






}
