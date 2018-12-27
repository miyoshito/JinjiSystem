package aimyamaguchi.co.jp.aimspringsql.curriculum;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.*;
import aimyamaguchi.co.jp.aimspringsql.employee.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;


@Service
public class CurriculumService {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    DbmsRepository dbmsr;
    @Autowired
    OsRepository osr;
    @Autowired
    DutyRepository dutyr;
    @Autowired
    LangRepository langr;
    @Autowired
    MakerRepository makerr;
    @Autowired
    ToolsRepository toolsr;
    @Autowired
    IndustryDataRepository industryr;
    @Autowired
    IndustryRepository indListr;
    @Autowired
    AssignRepository assignr;
    @Autowired
    EmployeeRepository err;
    @Autowired
    CurriculumRepository cir;

    public Map<String, Object> generateCvMap() {

        Map<String, Object> map = new HashMap<>();
        map.put("DBMS", dbmsr.findAll());
        map.put("OS", osr.findAll());
        map.put("DUTY", dutyr.findAll());
        map.put("LANG", langr.findAll());
        map.put("MAKER", makerr.findAll());
        map.put("TOOLS", toolsr.findAll());
        map.put("ASSIGN", assignr.findAll());

        return map;
    }

    public List<INDUSTRYData> getIndustryList() {
        return industryr.findAll();
    }

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


        if (validator(id)) queryParam.add("and m.sha_no = '" + id + "'\n");
        if (validator(name)) queryParam.add("and m.sha_name like '%" + name + "%'\n");
        if (validator(kana)) queryParam.add("and m.sha_kana like '%" + kana + "%'\n");
        if (validator(recruit)) queryParam.add("and m.sha_recruit = '" + recruit + "'\n");
        if (validator(age)) queryParam.add("and year(getdate()) - year(m.sha_birthday) = " + age + "\n");
        if (validator(customerName)) queryParam.add("and sho.cv_customer like '%" + customerName + "%'\n");
        if (validator(targetBusiness)) queryParam.add("and sho.cv_targetbusiness like '%" + targetBusiness + "%'\n");

        if (validator(experience) && validator(operator)) {
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
        if (validator(indType)) queryParam.add("and gyo.industry_type_desc like '%" + indType + "%'\n");

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
            if(validator(id)) subQuery.add("m.sha_no ="+id);
            if(validator(name)) subQuery.add("m.sha_name like '%"+name+"%'");
            if(validator(kana)) subQuery.add("m.sha_kana like '%"+kana+"%'");
            if(validator(recruit)) subQuery.add("m.sha_recruit = '"+recruit+"'");

            String p2 = String.join("\n or ",subQuery);

            if (subQuery.size() > 0){
                Query q2 = entityManager.createNativeQuery("select distinct m.sha_no from m_shain m where "+p2);
                return q2.getResultList();
            }
        } else {return query.getResultList();}
        return new ArrayList<>();
    }

    public void softDeleteCV(Long id) {
        Optional<CurriculumModel> cv = cir.findById(id);
        if (cv.isPresent()) {
            cv.get().setDeleted(true);
            cir.save(cv.get());
        }
    }

    public void insertCV(CurriculumDAO cv) {
        CurriculumModel shokureki = new CurriculumModel();

        if (validator(cv.getId())) {
            shokureki.setId(cv.getId());
        } else {
            shokureki.setId(null);
        }

        shokureki.setCustomer(cv.getCustomer());
        shokureki.setAssignData(assignr.getOne(cv.getAssignData()));
        shokureki.setEmployee_id(err.findByShainId(cv.getEmployee_id()));
        shokureki.setStartdate(cv.getStartdate());
        shokureki.setEnddate(cv.getEnddate());
        shokureki.setTargetbusiness(cv.getTargetbusiness());
        shokureki.setActive(true);

        IndustryKeys k = new IndustryKeys();
        k.setIndustryid(industryr.getOne(cv.getIndustryType()));
        k.setClassid(cv.getIndustryClass());

        INDUSTRYData d = industryr.findById(cv.getIndustryType()).orElse(null);
        shokureki.setIndustryClassData(indListr.findOneById(k));
        shokureki.setLangData(cv.getLangData());
        shokureki.setOsData(cv.getOsData());
        shokureki.setToolsData(cv.getToolsData());
        shokureki.setDbmsData(cv.getDbmsData());
        shokureki.setResponseData(cv.getResponseData());
        shokureki.setMakerData(cv.getMakerData());

        cir.save(shokureki);
    }


    private boolean validator(String value) {
        return (value != null && !value.equals(""));
    }

    private boolean validator(Long value) {
        return (value != null);
    }

    private boolean validator(Integer value) {
        return (value != null);
    }


    public List<String> skillMapSearchParams(String id, String name, String kana, Integer affiliation) {
        ArrayList<String> queryParam = new ArrayList<>();

        if (validator(id)) queryParam.add("sha.sha_no = " + id + " and");
        if (validator(name)) queryParam.add("sha.sha_name like '%" + name + "%' and");
        if (validator(kana)) queryParam.add("sha.sha_kana like '%" + kana + "%' and");
        if (validator(affiliation)) queryParam.add("sho.affiliation_id = " + affiliation + " and");

        String param = String.join("\n", queryParam);

        Query query = entityManager.createNativeQuery(
                "SELECT DISTINCT\n" +
                        "sha_no\n" +
                        "from\n" +
                        "m_shain sha left join sha_shozoku ss\n" +
                        "on sha.sha_no = ss.employee_sha_no\n" +
                        "left join m_shozoku sho on ss.affiliation_affiliation_id = sho.affiliation_id\n" +
                        "where\n" + param.substring(0, param.length() - 4));

        return query.getResultList();
    }

    public List<SkillMap> getSkillMap(List<String> id, boolean lang, boolean os, boolean dbms, boolean tools, boolean maker, boolean duty, List<Integer> industries) {


        List<SkillMap> lsm = new ArrayList<>();

        List<EmployeeMaster> eml = err.findByShainIdIn(id);

        String affiliation;

        for (EmployeeMaster em : eml) {
            affiliation = String.join("/", em.getAffiliation().stream().map(AFFILIATIONData::getDesc).collect(Collectors.toList()));
            System.out.println(em.getShainId());

            lsm.add(new SkillMap(em.getShainId(), em.getShainName(), affiliation, buildSkillMapList(em, lang, os, dbms, tools, maker, duty, industries)));
        }

        return lsm;
    }


    private void ListGenerator(String desc, Integer experience) {

    }

    private Map<String, List<SkillMapUtil>> buildSkillMapList(EmployeeMaster em, boolean lang, boolean osy, boolean dbms, boolean tools, boolean maker, boolean duty, List<Integer> inds) {


        //Map<String, Integer> teste = new HashMap<>();

        Map<String, List<SkillMapUtil>> fml = new HashMap<>();
        List<SkillMapUtil> langMap = new ArrayList<>();
        List<SkillMapUtil> makerMap = new ArrayList<>();
        List<SkillMapUtil> osMap = new ArrayList<>();
        List<SkillMapUtil> dbmsMap = new ArrayList<>();
        List<SkillMapUtil> respMap = new ArrayList<>();
        List<SkillMapUtil> toolsMap = new ArrayList<>();
        //Map<String, List<SkillMapUtil>> industryMap = new HashMap<>();

        for (CurriculumModel cv : em.getCurriculum()) {
            if (!cv.isDeleted()) {
                if (lang)
                    cv.getLangData().stream().map(LANGData::getDesc).forEach(l -> langMap.add(new SkillMapUtil(l, cv.getExperienceTime())));
                if (maker)
                    cv.getMakerData().stream().map(MAKERData::getDesc).forEach(m -> makerMap.add(new SkillMapUtil(m, cv.getExperienceTime())));
                if (osy)
                    cv.getOsData().stream().map(OSData::getDesc).forEach(os -> osMap.add(new SkillMapUtil(os, cv.getExperienceTime())));
                if (dbms)
                    cv.getDbmsData().stream().map(DBMSData::getDesc).forEach(db -> dbmsMap.add(new SkillMapUtil(db, cv.getExperienceTime())));
                if (duty)
                    cv.getResponseData().stream().map(DUTYData::getDesc).forEach(db -> respMap.add(new SkillMapUtil(db, cv.getExperienceTime())));
                if (tools)
                    cv.getToolsData().stream().map(TOOLSData::getDesc).forEach(db -> toolsMap.add(new SkillMapUtil(db, cv.getExperienceTime())));

                if (validator(inds.size())) {
                    inds.forEach(id -> {
                        Optional<INDUSTRYData> tempInd = industryr.findById(id.longValue());
                        List<SkillMapUtil> mapSet = new ArrayList<>();
                        for (INDCLASSIFICATIONData industryClass : tempInd.get().getIndustryClass()) {
                            mapSet.add(new SkillMapUtil(industryClass.getDesc(), cv.getExperienceTime()));
                        }
                        fml.put(tempInd.get().getTdesc(), mapSet);
                    });
                }
            }
        }

        if (validator(langMap.size())) {
            }
        if (validator(makerMap.size())) {
            makerMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("MAKER", makerMap);
        }
        if (validator(osMap.size())) {
            osMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("OS", osMap);
        }
        if (validator(dbmsMap.size())) {
            dbmsMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("DBMS", dbmsMap);
        }
        if (validator(respMap.size())) {
            respMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("DUTY", respMap);
        }
        if (validator(toolsMap.size())) {
            toolsMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("TOOLS", toolsMap);
        }
        return fml;

    }

}
