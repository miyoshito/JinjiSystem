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
    private String commendation_name;

    @Column(name = "RH_RESULT")
    private String commendation_result;

    @Column(name = "RH_YYYY")
    private int commendation_year;

    @Column(name = "RH_MM")
    private int commendation_month;

    @ManyToOne
    @JoinColumn(name="RH_RESUME")
    @JsonIgnore
    private ResumeModel h_resume;
}
