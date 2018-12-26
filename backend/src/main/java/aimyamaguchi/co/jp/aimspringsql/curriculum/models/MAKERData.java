package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import lombok.Data;

@Data
@Entity
@Table(name="[M_MAKER]", schema="[dbo]")
public class MAKERData{
    
    @Id
    @Column(name="MAKER_ID")
    private Long id;
    @Column(name="MAKER_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experienceTime;
    
    @ManyToMany(mappedBy="makerData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
    
}