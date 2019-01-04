package aimyamaguchi.co.jp.aimspringsql.constants;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.*;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.AffiliationRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.CarmodelRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.PositionRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.WorkAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstantsViewBuilder {

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


    public Map<String, Object> getEmpMasterParams(){

        Map<String,Object> map = new HashMap<>();

        map.put("AREA", workarea.findAll());
        map.put("POSITION", position.findAll());
        map.put("AFFILIATION", affiliation.findAll());
        map.put("CARMODEL", carmodel.findAll());

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
