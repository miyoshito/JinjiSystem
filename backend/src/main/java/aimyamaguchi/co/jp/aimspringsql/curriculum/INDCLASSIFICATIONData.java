package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="[M_GYKUBUN]", schema="[dbo]")
@Data
public class INDCLASSIFICATIONData implements Serializable {


    @EmbeddedId
    private IndustryKeys id;
    @Column(name="INDUSTRY_CLASS_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @JoinColumn(name="INDUSTRY_ID")
    @ManyToOne
    @MapsId("industry")
    @JsonIgnore
    private INDUSTRYData industry;

    @OneToMany(mappedBy = "industryclass")
    @JsonIgnore
    private List<CurriculumModel> curriculum;


}

