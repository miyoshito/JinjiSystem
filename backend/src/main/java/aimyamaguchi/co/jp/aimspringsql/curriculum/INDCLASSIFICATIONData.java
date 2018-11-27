package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="[M_GYKUBUN]", schema="[dbo]")
@Data
@ToString
public class INDCLASSIFICATIONData implements Serializable {


    @EmbeddedId
    private IndustryKeys id;
    @Column(name="INDUSTRY_CLASS_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;

    @ManyToOne
    @MapsId("industryid")
    @JoinColumn(name="INDUSTRY_TYPE_ID")
    private INDUSTRYData industryid;





}

