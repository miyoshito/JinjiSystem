package aimyamaguchi.co.jp.aimspringsql.resume;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.files.ResumeFileDetails;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name="[M_RIREKISHO]", schema="[DBO]")
@EqualsAndHashCode
public class ResumeModel {

    @Id
    @Column(name="RI_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    @OneToOne
    @JoinColumn(name="RI_SHA", nullable = false)
    @MapsId
    private EmployeeMaster employee;

    @OneToMany(mappedBy="resume", fetch = FetchType.EAGER)
    private Set<ResumeFileDetails> files;

    @OneToMany(mappedBy="k_resume", fetch = FetchType.EAGER)
    private Set<Career> careers;

    @OneToMany(mappedBy = "s_resume", fetch = FetchType.EAGER)
    private Set<Qualification> qualifications;

    @OneToMany(mappedBy = "h_resume", fetch = FetchType.EAGER)
    private Set<Commendation> commendations;

}