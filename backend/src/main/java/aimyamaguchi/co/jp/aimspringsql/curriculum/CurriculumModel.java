package aimyamaguchi.co.jp.aimspringsql.curriculum;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import lombok.Data;

@Entity
@Table(name="[M_SHOKUMUKEIREKI]", schema="[dbo]")
@Data
public class CurriculumModel {

    @Id
    private long id;

    private Date startperiod;
    private Date endperiod;

    private String customer; //(isso pode ser um m2m no futuro)

    @ManyToOne
    @JoinColumn(name="CV_INDUSTRY")
    private INDUSTRYData industry;

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
    private EmployeeMaster employee;

    

    


}