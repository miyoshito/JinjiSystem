package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import lombok.Data;

@Entity
@Table(name="[M_SHOKUMUKEIREKI]", schema="[dbo]")
@Data
public class CurriculumModel {

    @Id
    @Column(name="CV_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="CV_START")
    private LocalDate startdate;

    @Column(name="CV_END")
    private LocalDate enddate;

    @Column(name="CV_CUSTOMER")
    private String customer; //(isso pode ser um m2m no futuro)

    @Column(name="ACTIVE")
    private boolean active;

    @Column(name="DELETED")
    private boolean deleted;

    @Transient
    private Long experienceTime;
    @Transient
    public Integer getExperienceTime(){
        Period period = Period.between(this.startdate.withDayOfMonth(1), this.enddate.withDayOfMonth(1));
        if(period.getYears() > 0){
            return (period.getMonths() + (period.getYears() * 12));
        }
        else
        return period.getMonths();
    }



    @Transient
    public String industryClass;
    @Transient
    public String industryType;
    @Transient
    private Long industryTypeId;
    @Transient
    private Long industryClassId;
    @Transient
    public Long getIndustryTypeId() {
        return this.industryClassData.getId().getIndustryid().getId();
    }
    @Transient
    public Long getIndustryClassId() {
        return this.industryClassData.getId().getClassid();
    }
    @Transient
    public String getIndustryType(){
        return this.industryClassData.getId().getIndustryid().getTdesc();
    }
    @Transient
    public String getIndustryClass(){
        return this.industryClassData.getDesc();
    }

    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name="CV_INDUSTRY_TYPE", referencedColumnName = "INDUSTRY_TYPE_ID"),
            @JoinColumn(name="CV_INDUSTRY_CLASS", referencedColumnName ="INDUSTRY_CLASS_ID")
    })
    @JsonIgnore
    private INDCLASSIFICATIONData industryClassData;

    @Column(name="CV_TARGETBUSINESS")
    private String targetbusiness;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CV_MAKER")
    private Set<MAKERData> makerData;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CV_OS")
    private Set<OSData> osData;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CV_DBMS")
    private Set<DBMSData> dbmsData;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CV_RESPONSE")
    private Set<DUTYData> responseData;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CV_LANG")
    private Set<LANGData> langData;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CV_TOOLS")
    private Set<TOOLSData> toolsData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CV_ASSIGN")
    private ASSIGNData assignData;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="SHA_NO", nullable=false)
    private EmployeeMaster employee_id;
}