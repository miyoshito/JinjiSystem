package aimyamaguchi.co.jp.aimspringsql.constants;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import lombok.Data;

@Data
@Entity
@Table(name="[M_SHOZOKU]", schema="[dbo]")
public class AFFILIATIONData{
    
    @Id
    @Column(name="AFFILIATION_ID")
    private Long id;
    @Column(name="AFFILIATION_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;
    
    @OneToMany(mappedBy="affiliation")
    @JsonIgnore
    private List<EmployeeMaster> employee;
   
    
}