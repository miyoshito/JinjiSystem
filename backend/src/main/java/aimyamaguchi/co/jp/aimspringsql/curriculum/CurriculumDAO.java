package aimyamaguchi.co.jp.aimspringsql.curriculum;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private List<MAKERData> makerData;
    private List<OSData> osData;
    private List<DBMSData> dbmsData;
    private List<DUTYData> responseData;
    private List<LANGData> langData;
    private List<TOOLSData> toolsData;
    private Long assignData;
    private String employee_id;

}
