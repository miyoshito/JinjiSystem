package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="M_GYKUBUN")
@Data
public class INDCLASSIFICATIONData {

    @Id
    @Column(name="INDUSTRY_CLASS_ID")
    private Long id;
    @Column(name="INDUSTRY_CLASS_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "INDUSTRY_ID")
    @JsonIgnore
    private INDUSTRYData industry;
}
