package aimyamaguchi.co.jp.aimspringsql.resume;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import lombok.Data;


@Data
@Entity
@Table(name="[M_RIREKISHO]", schema="[DBO]")
public class ResumeModel {

    @Id
    @Column(name="RI_ID")
    private Long resumeId;

    @Column(name="RI_STUDY_AREA")
    private String formation;

    @Column(name="RI_UNIVERSITYNAME")
    private String universityName;

    @Column(name="RI_GRADUATEDDATE")
    private Date graduationDate;

    @Column(name="LOG_REGISTERED")
    private Date insertDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="SHA_NO", nullable=false)
    private EmployeeMaster employee;

}