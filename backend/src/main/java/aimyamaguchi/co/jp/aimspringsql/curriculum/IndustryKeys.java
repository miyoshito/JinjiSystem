package aimyamaguchi.co.jp.aimspringsql.curriculum;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import java.io.Serializable;

@Embeddable
public class IndustryKeys implements Serializable{

    @Column(name="INDUSTRY_CLASS_ID")
    private Long classid; //

    private Long industry;

}
