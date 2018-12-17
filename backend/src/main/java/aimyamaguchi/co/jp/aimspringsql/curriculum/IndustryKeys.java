package aimyamaguchi.co.jp.aimspringsql.curriculum;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IndustryKeys implements Serializable{

    @ManyToOne
    @JoinColumn(name="INDUSTRY_TYPE_ID")
    private INDUSTRYData industryid;

    @Column(name="INDUSTRY_CLASS_ID")
    private Long classid;

    public INDUSTRYData getIndustryid() {
        return industryid;
    }

    public void setIndustryid(INDUSTRYData industryid) {
        this.industryid = industryid;
    }

    public Long getClassid() {
        return classid;
    }

    public void setClassid(Long classid) {
        this.classid = classid;
    }
}
