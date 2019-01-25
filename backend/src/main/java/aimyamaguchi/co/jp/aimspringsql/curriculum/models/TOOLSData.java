package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="M_TOOLS")
public class TOOLSData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TOOLS_ID")
    private Long id;
    @Column(name="TOOLS_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experienceTime;
    

    @ManyToMany(mappedBy="toolsData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;

}