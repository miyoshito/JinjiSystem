package aimyamaguchi.co.jp.aimspringsql.education;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.security.AuditableModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@ToString
@Table(name="M_KYOIKU")
public class StudyCourseModel extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sponsor;

    private String educationName;

    private LocalDate startPeriod;

    private LocalDate endPeriod;

    private String venue;

    private Integer tuitionFee;

    private Integer transportExpenses;

    private Integer hotelExpenses;

    private String overview;

    private boolean active;


    //for insert purposes only
    @Transient
    private String employee_id;

    @Transient
    private Integer totalExpenses = 0;

    @Transient
    public Integer getTotalExpenses(){
        return this.getTuitionFee() + this.getTransportExpenses() + this.getHotelExpenses();
    }
    public void setTotalExpenses(Integer totalExpenses){
        this.totalExpenses = totalExpenses;
    }


    @ManyToOne
    @JoinColumn(name="employee_id")
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

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDate startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(Integer tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public Integer getTransportExpenses() {
        return transportExpenses;
    }

    public void setTransportExpenses(Integer transportExpenses) {
        this.transportExpenses = transportExpenses;
    }

    public Integer getHotelExpenses() {
        return hotelExpenses;
    }

    public void setHotelExpenses(Integer hotelExpenses) {
        this.hotelExpenses = hotelExpenses;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EmployeeMaster getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeMaster employee) {
        this.employee = employee;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
}

