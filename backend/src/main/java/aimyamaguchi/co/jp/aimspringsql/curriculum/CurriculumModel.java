package aimyamaguchi.co.jp.aimspringsql.curriculum;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import lombok.Data;

@Entity
@Table(name="[M_SHOKUMUKEIREKI]", schema="[dbo]")
@Data
public class CurriculumModel {

    @Id
    @Column(name="CV_ID")
    private long id;
    @Column(name="CV_START")
    private Date startperiod;
    @Column(name="CV_END")
    private Date endperiod;

    @Column(name="CV_CUSTOMER")
    private String customer; //(isso pode ser um m2m no futuro)

    @ManyToOne
    @JoinColumns(value = {
        @JoinColumn(name="CV_INDUSTRY_TYPE", referencedColumnName = "INDUSTRY_TYPE_ID"),
        @JoinColumn(name="CV_INDUSTRY_CLASS", referencedColumnName ="INDUSTRY_CLASS_ID")
    })
    private INDCLASSIFICATIONData industry;

    @Column(name="CV_TARGETBUSINESS")
    private String targetbusiness;

    @ManyToMany
    @JoinTable(name="CV_MAKER")
    private List<MAKERData> makerData;

    @ManyToMany
    @JoinTable(name="CV_OS")
    private List<OSData> osData;
    
    @ManyToMany
    @JoinTable(name="CV_DBMS")
    private List<DBMSData> dbmsData;
    
    @ManyToMany
    @JoinTable(name="CV_RESPONSE")
    private List<DUTYData> responseData;
    
    @ManyToMany
    @JoinTable(name="CV_LANG")
    private List<LANGData> langData;
    
    @ManyToMany
    @JoinTable(name="CV_TOOLS")
    private List<TOOLSData> toolsData;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="SHA_NO", nullable=false)
    private EmployeeMaster employee_id;
}