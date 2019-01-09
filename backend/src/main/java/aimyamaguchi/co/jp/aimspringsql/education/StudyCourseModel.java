package aimyamaguchi.co.jp.aimspringsql.education;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@ToString
@Table(name="[M_KYOIKU]", schema="[dbo]")
public class StudyCourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sponsor;

    private String educationName;

    private LocalDate startPeriod;

    private LocalDate endPeriod;

    private String venue;

    private Integer tuitionFee = 0;

    private Integer transportExpenses = 0;

    private Integer hotelExpenses = 0;

    private String overview;

    private boolean active;

    private LocalDate updated;

    private String updatedby;

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

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public EmployeeMaster getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeMaster employee) {
        this.employee = employee;
    }
}

