package aimyamaguchi.co.jp.aimspringsql.curriculum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurriculumService {

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
    IndustryRepository industry;

    public Map<String, Object> generateCvMap(){

        Map<String,Object> map = new HashMap<>();
        map.put("DBMS",dbms.findAll());
        map.put("OS",os.findAll());
        map.put("DUTY",duty.findAll());
        map.put("LANG",lang.findAll());
        map.put("MAKER",maker.findAll());
        map.put("TOOLS",tools.findAll());
        return map;


    }

    public List<INDUSTRYData> industryList(){
        return industry.findAll();
    }

}
