package aimyamaguchi.co.jp.aimspringsql.constants;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.*;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.POSITIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.QPOSITIONData;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.*;
import aimyamaguchi.co.jp.aimspringsql.qualifications.QualificationsModel;
import aimyamaguchi.co.jp.aimspringsql.qualifications.QualificationsRepository;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConstantsViewBuilder {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AffiliationRepository affiliation;
    @Autowired
    private CarmodelRepository carmodel;
    @Autowired
    private PositionRepository position;
    @Autowired
    private WorkAreaRepository workarea;
    @Autowired
    private DbmsRepository dbmsr;
    @Autowired
    private OsRepository osr;
    @Autowired
    private DutyRepository dutyr;
    @Autowired
    private LangRepository langr;
    @Autowired
    private MakerRepository makerr;
    @Autowired
    private ToolsRepository toolsr;
    @Autowired
    private IndustryDataRepository industryr;
    @Autowired
    private IndustryRepository indListr;
    @Autowired
    private AssignRepository assignr;
    @Autowired
    private RecruitRepository recruit;
    @Autowired
    private QualificationsRepository qualificationsRepository;



    public Map<String, List<String>> getShikakuSearchableParams(){

        List<QualificationsModel> baseList = qualificationsRepository.findAll();


        Map<String, List<String>> map = new HashMap<>();

        map.put("sponsor",baseList.stream().map(QualificationsModel::getSponsor).collect(Collectors.toList()));
        map.put("qualname",baseList.stream().map(QualificationsModel::getqName).collect(Collectors.toList()));

        return map;
    }

    public Map<String, Object> getEmpMasterParams(){

        QPOSITIONData pos = QPOSITIONData.pOSITIONData;

        Map<String,Object> map = new HashMap<>();
        JPAQuery<POSITIONData> positions = new JPAQueryFactory(entityManager).selectFrom(pos).where(pos.id.loe(900000));
        map.put("AREA", workarea.findAll());
        map.put("POSITION", positions.fetch());
        map.put("AFFILIATION", affiliation.findAll());
        map.put("CARMODEL", carmodel.findAll());
        map.put("RECRUIT", recruit.findAll());

        return map;
    }

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
}
