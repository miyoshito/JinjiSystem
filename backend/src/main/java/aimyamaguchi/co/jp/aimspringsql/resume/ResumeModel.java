package aimyamaguchi.co.jp.aimspringsql.resume;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.files.ResumeFileDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@Entity
@Table(name="[M_RIREKISHO]", schema="[DBO]")
public class ResumeModel {

    @Id
    @Column(name="RI_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @Column(name="RI_STUDY_AREA")
    private String formation;

    @Column(name="RI_UNIVERSITYNAME")
    private String universityName;

    @Column(name="RI_BUNRI")
    private String bunri;

    @Column(name="LOG_REGISTERED")
    private Date insertDate;

    @Column(name="LOG_INSERTEDBY")
    private String insertedBy;

    @JsonIgnore
    @OneToOne(mappedBy="resume")
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