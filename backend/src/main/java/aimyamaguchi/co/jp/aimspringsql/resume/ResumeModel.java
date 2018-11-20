package aimyamaguchi.co.jp.aimspringsql.resume;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.files.ResumeFileDetails;
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

    @Column(name="LOG_INSERTEDBY")
    private String insertedBy;



    @OneToOne
    @JsonIgnore
    @JoinColumn(name="SHA_NO", nullable=false)
    private EmployeeMaster employee;

    @OneToMany(mappedBy="resume")
    private List<ResumeFileDetails> files;

    @OneToMany(mappedBy="k_resume")
    private List<Career> careers;

    @OneToMany(mappedBy = "s_resume")
    private List<Qualification> qualifications;

    @OneToMany(mappedBy = "h_resume")
    private List<Commendation> commendations;

}