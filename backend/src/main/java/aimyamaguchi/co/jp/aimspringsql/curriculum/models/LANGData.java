package aimyamaguchi.co.jp.aimspringsql.curriculum.models;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="M_LANG")
public class LANGData{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LANG_ID")
    private Long id;
    @Column(name="LANG_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experienceTime;


    @ManyToMany(mappedBy="langData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
}