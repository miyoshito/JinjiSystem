package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class CurriculumDAO {

    private Long id;
    private LocalDate startdate;
    private LocalDate enddate;
    private String customer;
    private boolean active;
    private boolean deleted;
    public Long industryClass;
    public Long industryType;
    private String targetbusiness;
    private Set<MAKERData> makerData;
    private Set<OSData> osData;
    private Set<DBMSData> dbmsData;
    private Set<DUTYData> responseData;
    private Set<LANGData> langData;
    private Set<TOOLSData> toolsData;
    private Long assignData;
    private String employee_id;

}
