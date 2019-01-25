package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name="M_SHOZOKU")
public class AFFILIATIONData{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AFFILIATION_ID")
    private Long id;
    @Column(name="AFFILIATION_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;
    
    @ManyToMany(mappedBy="affiliation")
    @JsonIgnore
    private List<EmployeeMaster> employee;
    
}