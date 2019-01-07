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

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;


@Entity
@Table(name="[M_RIREKISHO]", schema="[DBO]")
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


/*    @OneToOne(optional = false)
    @JoinColumn(name="RI_SHA")
    @JsonBackReference
    private EmployeeMaster employee;*/

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "resume")
    private EmployeeMaster employee;

    @OneToMany(mappedBy="resume", fetch = FetchType.EAGER)
    private Set<ResumeFileDetails> files;

    @OneToMany(mappedBy="k_resume", fetch = FetchType.EAGER)
    private Set<Career> careers;

    @OneToMany(mappedBy = "s_resume", fetch = FetchType.EAGER)
    private Set<Qualification> qualifications;

    @OneToMany(mappedBy = "h_resume", fetch = FetchType.EAGER)
    private Set<Commendation> commendations;

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getBunri() {
        return bunri;
    }

    public void setBunri(String bunri) {
        this.bunri = bunri;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    @JsonBackReference
    public EmployeeMaster getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeMaster employee) {
        this.employee = employee;
    }

    public Set<ResumeFileDetails> getFiles() {
        return files;
    }

    public void setFiles(Set<ResumeFileDetails> files) {
        this.files = files;
    }

    public Set<Career> getCareers() {
        return careers;
    }

    public void setCareers(Set<Career> careers) {
        this.careers = careers;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public Set<Commendation> getCommendations() {
        return commendations;
    }

    public void setCommendations(Set<Commendation> commendations) {
        this.commendations = commendations;
    }
}