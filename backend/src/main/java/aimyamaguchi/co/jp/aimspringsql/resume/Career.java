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
    @Column(name = "RK_SCHOOL")
    private String career_desc;
    @Column(name = "RK_DEPARTMENT")
    private String career_depto;
    @Column(name = "RK_RESULT")
    private String career_result;
    @Column(name = "RK_YM")
    private Date career_date;

    @ManyToOne
    @JoinColumn(name="RK_RESUME")
    @JsonIgnore
    private ResumeModel k_resume;


}
