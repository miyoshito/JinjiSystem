package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="[M_GYOUMU]",schema ="[dbo]")
@Data
public class INDUSTRYData {


    @Id
    @Column(name="INDUSTRY_TYPE_ID")
    private Long id;
    @Column(name="INDUSTRY_TYPE_DESC")
    private String tdesc;
    @Column(name="ACTIVE")
    private boolean active;

    @OneToMany(mappedBy = "industryid")
    @JsonIgnore
    private Set<INDCLASSIFICATIONData> industryClass;


    /*
    @OneToMany(mappedBy = "industryType")
    private List<CurriculumModel> curriculum;
    */

}
