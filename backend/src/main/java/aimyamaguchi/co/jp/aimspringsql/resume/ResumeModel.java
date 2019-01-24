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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long resumeId;

    @Column(name="RI_STUDY_AREA")
    private String formation;

    @Column(name="RI_UNIVERSITYNAME")
    private String universityName;

    @Column(name="RI_BUNRI")
    private String bunri;

    @Column(name="RI_NOTES")
    private String notes;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "resume")
    private EmployeeMaster employee;

    @OneToMany(mappedBy="resume", fetch = FetchType.EAGER)
    private Set<ResumeFileDetails> files;

    @OneToMany(mappedBy="k_resume", fetch = FetchType.EAGER)
    @OrderBy("RK_YYYY DESC")
    private Set<Career> careers;

    @OneToMany(mappedBy = "s_resume", fetch = FetchType.EAGER)
    @OrderBy("RS_YYYY DESC")
    private Set<Qualification> qualifications;

    @OneToMany(mappedBy = "h_resume", fetch = FetchType.EAGER)
    @OrderBy("RH_YYYY DESC")
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}