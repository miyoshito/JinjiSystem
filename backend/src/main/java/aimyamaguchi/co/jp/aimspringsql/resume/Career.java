package aimyamaguchi.co.jp.aimspringsql.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Table(name="M_KEIREKI")
@Entity
@Data
public class Career {

    @Id
    @Column(name = "RK_NO")
    private Long career_id;

    @Column(name="RK_YYYY")
    private Long career_year;

    @Column(name="RK_MM")
    private Long career_month;

    @Column(name = "RK_SCHOOL_WORK")
    private String career_scwk;

    @Column(name = "RK_DEPARTMENT_AFFILIATION")
    private String career_dpaf;

    @Column(name = "RK_RESULT")
    private String career_result;

    @ManyToOne
    @JoinColumn(name="RK_RESUME")
    @JsonIgnore
    private ResumeModel k_resume;


}
