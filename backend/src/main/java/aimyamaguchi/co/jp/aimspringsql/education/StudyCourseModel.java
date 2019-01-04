package aimyamaguchi.co.jp.aimspringsql.education;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@ToString
@Table(name="[M_KYOIKU]", schema="[dbo]")
public class StudyCourseModel {

    @Id
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

    private LocalDate updated;

    private String updatedby;

    @Transient
    private Integer totalExpenses;

    @Transient
    public Integer getTotalExpenses(){
        return this.tuitionFee + this.transportExpenses + this.hotelExpenses;
    }


    @ManyToOne
    @JoinColumn(name="employee_id")
    @JsonIgnore
    private EmployeeMaster employee;
}

