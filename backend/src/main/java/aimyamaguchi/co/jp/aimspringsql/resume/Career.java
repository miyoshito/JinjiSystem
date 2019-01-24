package aimyamaguchi.co.jp.aimspringsql.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name="RRS_KEIREKI")
@Entity
@Data
public class Career implements Serializable {

    @Id
    @Column(name = "RK_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long careerid;

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

    @Column(name="ACTIVE")
    private boolean active;

    @ManyToOne
    @JoinColumn(name="RK_RESUME")
    @JsonIgnore
    private ResumeModel k_resume;
}
