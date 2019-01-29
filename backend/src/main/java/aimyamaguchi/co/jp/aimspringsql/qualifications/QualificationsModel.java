package aimyamaguchi.co.jp.aimspringsql.qualifications;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.security.AuditableModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="M_SHIKAKU")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QualificationsModel extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SH_ID")
    private Long id;

    @Column(name="SH_SPONSOR")
    private String sponsor;

    @Column(name="SH_QNAME")
    private String qName;

    @Column(name="SH_EXDATE")
    private LocalDate examDate;

    @Column(name="SH_EXAMINATIONPLACE")
    private String examPlace;

    @Column(name="SH_RESULTS")
    private String results;

    @Column(name="SH_EXAMCOST")
    private Integer examFee;

    @Column(name="SH_EXTRACOST")
    private Integer extraFee;

    @Column(name="SH_COMPANYBURDEN")
    private Integer coveredFee;

    @Column(name="SH_REWARD")
    private Integer reward;

    private boolean active;

    @Transient
    private Integer cost;

    @Transient
    private String employee_id;

    @Transient
    private String employee_name;

    @Transient
    private boolean shainRetired;


    @ManyToOne
    @JoinColumn(name = "SH_SHAINID")
    @JsonIgnore
    private EmployeeMaster employee;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public Integer getExamFee() {
        return examFee;
    }

    public void setExamFee(Integer examFee) {
        this.examFee = examFee;
    }

    public Integer getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(Integer extraFee) {
        this.extraFee = extraFee;
    }

    public Integer getCoveredFee() {
        return coveredFee;
    }

    public void setCoveredFee(Integer coveredFee) {
        this.coveredFee = coveredFee;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getCost() {
        return this.examFee + this.extraFee;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public EmployeeMaster getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeMaster employee) {
        this.employee = employee;
    }

    public String getEmployee_id() {
        return this.employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return this.employee_name;
    }

    public void setEmployee_name(String employee_name){
        this.employee_name = employee_name;
    }

    public boolean isShainRetired() {
        return this.shainRetired;
    }

    public void setShainRetired(boolean shainRetired) {
        this.shainRetired = shainRetired;
    }
}
