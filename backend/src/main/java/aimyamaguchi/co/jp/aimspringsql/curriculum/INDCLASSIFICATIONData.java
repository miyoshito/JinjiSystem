package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="[M_GYKUBUN]", schema="[dbo]")
public class INDCLASSIFICATIONData implements Serializable {


    @EmbeddedId
    private IndustryKeys id;

    @Transient
    private Long classid;

    @Column(name="INDUSTRY_CLASS_DESC")
    private String desc;

    @Column(name="ACTIVE")
    private boolean active;

    public Long getClassId(){ return this.id.getClassid();}

    @JsonIgnore
    public IndustryKeys getId() {
        return id;
    }

    public void setId(IndustryKeys id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

