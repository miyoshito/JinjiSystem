package aimyamaguchi.co.jp.aimspringsql.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Table(name="M_HYOSHO")
@Entity
@Data
public class Commendation {


    @Id
    @Column(name = "RH_NO")
    private Long commendation_id;
    @Column(name = "RH_CONTENTS")
    private String commendation_desc;
    @Column(name = "RH_RESULT")
    private String commendation_result;
    @Column(name = "RH_YM")
    private Date commendation_date;

    @ManyToOne
    @JoinColumn(name="RH_RESUME")
    @JsonIgnore
    private ResumeModel h_resume;
}
