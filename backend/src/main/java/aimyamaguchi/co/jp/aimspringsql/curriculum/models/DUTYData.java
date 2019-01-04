package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="[M_RESPONSE]", schema="[dbo]")
public class DUTYData {
    
    @Id
    @Column(name="RESPONSE_ID")
    private Long id;
    @Column(name="RESPONSE_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experienceTime;
    

    @ManyToMany(mappedBy="responseData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
    
}