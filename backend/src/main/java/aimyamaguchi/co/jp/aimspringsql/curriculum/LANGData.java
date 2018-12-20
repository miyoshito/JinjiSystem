package aimyamaguchi.co.jp.aimspringsql.curriculum;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import lombok.Data;

@Data
@Entity
@Table(name="[M_LANG]", schema="[dbo]")
public class LANGData{
    
    @Id
    @Column(name="LANG_ID")
    private Long id;
    @Column(name="LANG_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @Transient
    private Integer experience;

    @ManyToMany(mappedBy="langData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
}