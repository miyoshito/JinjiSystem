package aimyamaguchi.co.jp.aimspringsql.skillmap;

import aimyamaguchi.co.jp.aimspringsql.authfilters.CustomException;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.IndustryDataRepository;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.LangRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMin;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private SearchFilters sf;

    public List<String> skillMapSearchParams(String id) {

        Map<EmployeeMin, List<Map<String,Integer>>> f = new HashMap<>(); //mapa final que vai ser liberado no post.
        List<Map<String,Integer>> eee = new ArrayList<>();
        Map<String, Integer> azaz = new HashMap<>();

        List<SkillMapUtil> listHolder = new ArrayList<>();

        //fazer o stream do mapa pra ver quais params ele pede, essa funcao precisa receber um ID e o que precisar.

        //adicionando o usuario no mapa
        EmployeeMaster u = sf.getEmployeeWithCv(id);
        EmployeeMin rapidao = new EmployeeMin(
                u.getShainId(),
                u.getShainName(),
                u.getAffiliation(),
                u.isAdmin());

        //buildando o mapa

        for (CurriculumModel cv : u.getCurriculum()) {
            if (!cv.isDeleted()) {

                    cv.getLangData()
                            .stream()
                            .map(LANGData::getDesc)
                            .forEach(l -> listHolder
                                    .add(new SkillMapUtil(l, cv.getExperienceTime())));

                    azaz = listHolder.stream()
                            .collect(Collectors.groupingBy(
                                    SkillMapUtil::getDescription,
                                    Collectors.summingInt(
                                            SkillMapUtil::getExperience)));

                    /*cv.getMakerData().stream().map(MAKERData::getDesc).forEach(m -> listHolder.add(new SkillMapUtil(m, cv.getExperienceTime())));

                    cv.getOsData().stream().map(OSData::getDesc).forEach(os -> listHolder.add(new SkillMapUtil(os, cv.getExperienceTime())));

                    cv.getDbmsData().stream().map(DBMSData::getDesc).forEach(db -> listHolder.add(new SkillMapUtil(db, cv.getExperienceTime())));

                    cv.getResponseData().stream().map(DUTYData::getDesc).forEach(db -> listHolder.add(new SkillMapUtil(db, cv.getExperienceTime())));

                    cv.getToolsData().stream().map(TOOLSData::getDesc).forEach(db -> listHolder.add(new SkillMapUtil(db, cv.getExperienceTime())));*/

                /*if (validator.isNullValidator(inds.size())) {
                    inds.forEach(id -> {
                        Optional<INDUSTRYData> tempInd = industry.findById(id.longValue());
                        List<SkillMapUtil> mapSet = new ArrayList<>();
                        for (INDCLASSIFICATIONData industryClass : tempInd.get().getIndustryClass()) {
                            mapSet.add(new SkillMapUtil(industryClass.getDesc(), cv.getExperienceTime()));
                        }
                        fml.put(tempInd.get().getTdesc(), mapSet);
                    });
                }*/
            }
        }
        eee.add(azaz);
        f.put(rapidao,eee);

        System.out.println(f);

        return null;
    }


    /*
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
    }*/
}
