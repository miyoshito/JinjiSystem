package aimyamaguchi.co.jp.aimspringsql.skillmap;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.IndustryDataRepository;
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

import static java.util.stream.Collectors.reducing;

@Service
public class SkillMapService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepository er;
    @Autowired
    private IndustryDataRepository industry;
    @Autowired
    private CustomValidators validator;

    public List<String> skillMapSearchParams(String id, String name, String kana, Integer affiliation) {
        ArrayList<String> queryParam = new ArrayList<>();

        if (validator.isNullValidator(id)) queryParam.add("sha.sha_no = " + id + " and");
        if (validator.isNullValidator(name)) queryParam.add("sha.sha_name like '%" + name + "%' and");
        if (validator.isNullValidator(kana)) queryParam.add("sha.sha_kana like '%" + kana + "%' and");
        if (validator.isNullValidator(affiliation)) queryParam.add("sho.affiliation_id = " + affiliation + " and");

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
        List<EmployeeMaster> eml = er.findByShainIdIn(id);
        String affiliation;
        for (EmployeeMaster em : eml) {
            affiliation = String.join("/", em.getAffiliation().stream().map(AFFILIATIONData::getDesc).collect(Collectors.toList()));
            System.out.println(em.getShainId());
            lsm.add(new SkillMap(em.getShainId(), em.getShainName(), affiliation, buildSkillMapList(em, lang, os, dbms, tools, maker, duty, industries)));
        }
        return lsm;
    }

    private Map<String, List<SkillMapUtil>> buildSkillMapList(EmployeeMaster em, boolean lang, boolean osy, boolean dbms, boolean tools, boolean maker, boolean duty, List<Integer> inds) {

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

                if (validator.isNullValidator(inds.size())) {
                    inds.forEach(id -> {
                        Optional<INDUSTRYData> tempInd = industry.findById(id.longValue());
                        List<SkillMapUtil> mapSet = new ArrayList<>();
                        for (INDCLASSIFICATIONData industryClass : tempInd.get().getIndustryClass()) {
                            mapSet.add(new SkillMapUtil(industryClass.getDesc(), cv.getExperienceTime()));
                        }
                        fml.put(tempInd.get().getTdesc(), mapSet);
                    });
                }
            }
        }

        if (validator.isNullValidator(langMap.size())) {
            System.out.println("Eita rapaz...");
        }
        if (validator.isNullValidator(makerMap.size())) {
            makerMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("MAKER", makerMap);
        }
        if (validator.isNullValidator(osMap.size())) {
            osMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("OS", osMap);
        }
        if (validator.isNullValidator(dbmsMap.size())) {
            dbmsMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("DBMS", dbmsMap);
        }
        if (validator.isNullValidator(respMap.size())) {
            respMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("DUTY", respMap);
        }
        if (validator.isNullValidator(toolsMap.size())) {
            toolsMap.stream().collect(Collectors.groupingBy(SkillMapUtil::getDescription, Collectors.collectingAndThen(reducing((a, b) -> new SkillMapUtil(a.description, (a.experience += b.experience))), Optional::get)));
            fml.put("TOOLS", toolsMap);
        }
        return fml;
    }
}
