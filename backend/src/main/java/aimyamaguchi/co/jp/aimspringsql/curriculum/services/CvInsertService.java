package aimyamaguchi.co.jp.aimspringsql.curriculum.services;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumDAO;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.IndustryKeys;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.AssignRepository;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.CurriculumRepository;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.IndustryDataRepository;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.IndustryRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CvInsertService {

    @Autowired
    private CustomValidators validator;

    @Autowired
    private EmployeeRepository er;

    @Autowired
    private IndustryDataRepository industryDataRepository;

    @Autowired
    private IndustryRepository industryTypeRepository;

    @Autowired
    private AssignRepository assign;

    @Autowired
    private CurriculumRepository cr;

    public void insertCV(CurriculumDAO cv) {
        CurriculumModel shokureki = new CurriculumModel();

        if (validator.isNullValidator(cv.getId())) {
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
        k.setIndustryid(industryDataRepository.getOne(cv.getIndustryType()));
        k.setClassid(cv.getIndustryClass());

        INDUSTRYData d = industryDataRepository.findById(cv.getIndustryType()).orElse(null);
        shokureki.setIndustryClassData(industryTypeRepository.findOneById(k));
        shokureki.setLangData(cv.getLangData());
        shokureki.setOsData(cv.getOsData());
        shokureki.setToolsData(cv.getToolsData());
        shokureki.setDbmsData(cv.getDbmsData());
        shokureki.setResponseData(cv.getResponseData());
        shokureki.setMakerData(cv.getMakerData());

        cr.save(shokureki);
    }
}
