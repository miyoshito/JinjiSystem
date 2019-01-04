package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name="[M_SHOZOKU]", schema="[dbo]")
public class AFFILIATIONData{
    
    @Id
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