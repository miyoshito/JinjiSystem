package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="M_GYOUMU")
@Data
public class INDUSTRYData {


    @Id
    @Column(name="INDUSTRY_TYPE_ID")
    private Long id;
    @Column(name="INDUSTRY_TYPE_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @OneToMany(mappedBy = "industry")
    private List<INDCLASSIFICATIONData> classification;

    @OneToMany(mappedBy="industry")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
}
