package aimyamaguchi.co.jp.aimspringsql.curriculum;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import java.util.stream.Collectors;


@Service
public class CurriculumService {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    DbmsRepository dbms;
    @Autowired
    OsRepository os;
    @Autowired
    DutyRepository duty;
    @Autowired
    LangRepository lang;
    @Autowired
    MakerRepository maker;
    @Autowired
    ToolsRepository tools;
    @Autowired
    IndustryDataRepository industry;
    @Autowired
    IndustryRepository indList;
    @Autowired
    AssignRepository assign;
    @Autowired
    EmployeeRepository er;
    @Autowired
    CurriculumInterface ci;

    public Map<String, Object> generateCvMap(){

        Map<String,Object> map = new HashMap<>();
        map.put("DBMS",dbms.findAll());
        map.put("OS",os.findAll());
        map.put("DUTY",duty.findAll());
        map.put("LANG",lang.findAll());
        map.put("MAKER",maker.findAll());
        map.put("TOOLS",tools.findAll());
        map.put("ASSIGN", assign.findAll());

        return map;
    }

    public List<INDUSTRYData> getIndustryList(){
        return industry.findAll();
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
    ){
        ArrayList<String> queryJoins = new ArrayList<>();
        ArrayList<String> queryParam = new ArrayList<>();

        if (validator(id)) queryParam.add("and m.sha_no = '"+id+"'\n");
        if (validator(name)) queryParam.add("and m.sha_name like '%"+name+"%'\n");
        if (validator(kana)) queryParam.add("and m.sha_kana like '%"+kana+"%'\n");
        if (validator(recruit)) queryParam.add("and m.sha_recruit = '"+recruit+"'\n");
        if (validator(age)) queryParam.add("and year(getdate()) - year(m.sha_birthday) = "+age+"\n");
        if (validator(customerName)) queryParam.add("and sho.cv_customer like '%"+customerName+"%'\n");
        if (validator(targetBusiness)) queryParam.add("and sho.cv_targetbusiness like '%"+targetBusiness+"%'\n");

        if(validator(experience) && validator(operator)){
            switch(operator){
                case "equals":
                    queryParam.add("and datediff(year, sho.cv_start, sho.cv_end) = "+ experience);
                    break;
                case "greaterthan":
                    queryParam.add("and datediff(year, sho.cv_start, sho.cv_end) >= "+ experience);
                    break;
                case "lessthan":
                    queryParam.add("and datediff(year, sho.cv_start, sho.cv_end) <= "+ experience);
                    break;
            }

        }
        if (validator(indType)) queryParam.add("and gyo.industry_type_desc like '%"+indType+"%'\n");

        if((dbms != null) && (dbms.size() > 0)){
            String dbmsin = String.join(",", dbms);
            queryParam.add("and ddbms.dbms_id in("+ dbmsin+")");

            queryJoins.add("\n" +
                    "left outer join cv_dbms dbms on dbms.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_dbms ddbms on ddbms.dbms_id = dbms.dbms_data_dbms_id");
        }
        if((os != null) && (os.size() > 0)){
            String osin = String.join(",", os);
            queryParam.add("and dos.os_id in ("+osin+")");
            queryJoins.add("\n" +
                    "left outer join cv_os os on os.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_os dos on dos.os_id = os.os_data_os_id");
        }

        if(lang != null && lang.size() > 0){
            String langin = String.join(",", lang);
            queryParam.add("and dlang.lang_id in ("+ langin +")");

            queryJoins.add("\n" +
                    "left outer join cv_lang lang on lang.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_lang dlang on dlang.lang_id = lang.lang_data_lang_id");
        }

        if(tools != null && tools.size() >0){
            String toolsin = String.join(",",tools);
            queryParam.add("and dtools.tools_id in ("+toolsin+")");

            queryJoins.add("\n" +
                    "left outer join cv_tools tools on tools.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_tools dtools on dtools.tools_id = tools.tools_data_tools_id");
        }
        if (response != null && response.size() > 0){
            String responsein = String.join(",",response);
            queryParam.add("and dresponse.response_id in ("+responsein+")");

            queryJoins.add("\n" +
                    "left outer join cv_response response on response.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_response dresponse on dresponse.response_id = response.response_data_response_id");
        }

        if (maker != null && maker.size() > 0) {
            String makerin = String.join(",",maker);
            queryParam.add("and dmaker.maker_id in ("+makerin+")");

            queryJoins.add("\n" +
                    "left outer join cv_maker maker on maker.curriculum_cv_id = sho.cv_id\n" +
                    "left outer join m_maker dmaker on dmaker.maker_id = maker.maker_data_maker_id");
        }

        String param = String.join("\n", queryParam);
        String joins = String.join("\n", queryJoins);
        Query query = entityManager.createNativeQuery(
                "select DISTINCT m.sha_no\n"
                +"from\n"
                +"m_shain m join m_shokumukeireki sho on m.sha_no = sho.sha_no\n"
                +"left outer join m_gyoumu gyo  on gyo.industry_type_id = sho.cv_industry_type\n"
                +"left outer join m_gykubun gyk on sho.cv_industry_class = gyk.industry_class_id\n"
                + joins
                + "\nwhere\n"
                + "gyk.industry_type_id = gyo.industry_type_id\n"
                + param);

        return query.getResultList();
    }

    public void softDeleteCV(Long id){
        Optional<CurriculumModel> cv = ci.findById(id);
        if (cv.isPresent()) {
            cv.get().setDeleted(true);
            ci.save(cv.get());
        }
    }

    public void insertCV(CurriculumDAO cv){
        CurriculumModel shokureki = new CurriculumModel();

        if (validator(cv.getId())) {
            shokureki.setId(cv.getId());
        } else {
            shokureki.setId(null);
        }

        shokureki.setCustomer(cv.getCustomer());
        shokureki.setAssignData(assign.getOne(cv.getAssignData()));
        shokureki.setEmployee_id(er.findByShainId(cv.getEmployee_id()));
        shokureki.setStartdate(cv.getStartdate());
        shokureki.setEnddate(cv.getEnddate());
        shokureki.setTargetbusiness(cv.getTargetbusiness());
        shokureki.setActive(true);

        IndustryKeys k = new IndustryKeys();
        k.setIndustryid(industry.getOne(cv.getIndustryType()));
        k.setClassid(cv.getIndustryClass());

        INDUSTRYData d = industry.findById(cv.getIndustryType()).orElse(null);
        shokureki.setIndustryClassData(indList.findOneById(k));
        shokureki.setLangData(cv.getLangData());
        shokureki.setOsData(cv.getOsData());
        shokureki.setToolsData(cv.getToolsData());
        shokureki.setDbmsData(cv.getDbmsData());
        shokureki.setResponseData(cv.getResponseData());
        shokureki.setMakerData(cv.getMakerData());

        ci.save(shokureki);
    }


    private boolean validator(String value){
        return (value != null && !value.equals(""));
    }
    private boolean validator(Long value){
        return (value != null);
    }


    public List<SkillMap> fml(String id){

        EmployeeMaster em = er.findByShainId(id);

        List<SkillMap> lsm = new ArrayList<>();
        lsm.add(new SkillMap(em.getShainId(), em.getShainName(), "fkinfield", buildSkillMapList(er.findByShainId(id))));

        return lsm;
    }


    private Map<String, Map<String,SkillMapUtil>> buildSkillMapList(EmployeeMaster em){

        Map<String, Map<String,SkillMapUtil>> params = new HashMap<>();

        Map<String, SkillMapUtil> fml = new HashMap<>();
        List<SkillMapUtil> langMap = new ArrayList<>();
        List<SkillMapUtil> makerMap = new ArrayList<>();
        List<SkillMapUtil> osMap = new ArrayList<>();
        List<SkillMapUtil> dbmsMap = new ArrayList<>();
        List<SkillMapUtil> respMap = new ArrayList<>();
        List<SkillMapUtil> toolsMap = new ArrayList<>();
        List<SkillMapUtil> industryMap = new ArrayList<>();

        for(CurriculumModel cv : em.getCurriculum()) {
            cv.getLangData().stream().map(LANGData::getDesc).forEach(l -> langMap.add(new SkillMapUtil(l,cv.getExperienceTime())));
            cv.getMakerData().stream().map(MAKERData::getDesc).forEach(m -> makerMap.add(new SkillMapUtil(m,cv.getExperienceTime())));
            cv.getOsData().stream().map(OSData::getDesc).forEach(os -> osMap.add(new SkillMapUtil(os, cv.getExperienceTime())));
            cv.getDbmsData().stream().map(DBMSData::getDesc).forEach(db -> dbmsMap.add(new SkillMapUtil(db, cv.getExperienceTime())));
            cv.getResponseData().stream().map(DUTYData::getDesc).forEach(db -> respMap.add(new SkillMapUtil(db, cv.getExperienceTime())));
            cv.getToolsData().stream().map(TOOLSData::getDesc).forEach(db -> toolsMap.add(new SkillMapUtil(db, cv.getExperienceTime())));
        }
        //em.getCurriculum().forEach(cu -> industryMap.add(new SkillMapUtil(cu.industryType, cu.getExperienceTime())));

        //LANG

        /*fml = industryMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse(new SkillMapUtil(e.getKey(), 0))));*/

//        params.put("INDUSTRY",fml);

        fml = langMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse(new SkillMapUtil(e.getKey(), 0))));
        params.put("LANG", fml);
        //MAKER
        fml = makerMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse( new SkillMapUtil(e.getKey(), 0))));
        params.put("MAKER", fml);

        //OS
        fml = osMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse( new SkillMapUtil(e.getKey(), 0))));
        params.put("OS", fml);


        fml = dbmsMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse( new SkillMapUtil(e.getKey(), 0))));
        params.put("DBMS", fml);

        fml = respMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse( new SkillMapUtil(e.getKey(), 0))));
        params.put("DUTY", fml);


        fml = toolsMap.stream()
                .collect(Collectors.groupingBy(SkillMapUtil::getDescription))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce((acc, val) -> new SkillMapUtil(acc.description, (acc.experience += val.experience)))
                                .orElse( new SkillMapUtil(e.getKey(), 0))));
        params.put("TOOLS", fml);


        return params;

    }
}
