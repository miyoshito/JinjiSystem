package aimyamaguchi.co.jp.aimspringsql.curriculum.models;


import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="M_OS")
public class OSData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OS_ID")
    private Long id;
    @Column(name="OS_DESC")
    private String desc;  
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experienceTime;
    
    @ManyToMany(mappedBy="osData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
    
}