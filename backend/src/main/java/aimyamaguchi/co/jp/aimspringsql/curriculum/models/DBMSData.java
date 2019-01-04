package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="[M_DBMS]", schema="[dbo]")
public class DBMSData{
    @Id
    @Column(name="DBMS_ID")
    private Long id;
    @Column(name="DBMS_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experienceTime;

    
    @ManyToMany(mappedBy="dbmsData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
    

}