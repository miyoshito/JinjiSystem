package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="M_WAREA")
public class WorkAreaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="WAREA_ID")
    private Long id;
    @Column(name="WAREA_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;
    

    @OneToMany(mappedBy="shainArea")
    @JsonIgnore
    private List<EmployeeMaster> employee;

}