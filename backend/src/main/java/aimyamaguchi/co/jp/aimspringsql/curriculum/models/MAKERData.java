package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="M_MAKER")
public class MAKERData{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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