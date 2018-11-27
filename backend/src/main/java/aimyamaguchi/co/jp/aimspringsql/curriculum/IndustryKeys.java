package aimyamaguchi.co.jp.aimspringsql.curriculum;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


import java.io.Serializable;
import java.util.List;

@Embeddable
@Data
@ToString
public class IndustryKeys implements Serializable{


    private Long industryid;

    @Column(name="INDUSTRY_CLASS_ID")
    private Long classid; //

}
