package aimyamaguchi.co.jp.aimspringsql.qualifications;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name="[M_SHIKAKU]", schema = "[DBO]")
public class QualificationsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SH_ID")
    private Long id;

    @Column(name="SH_SPONSOR")
    private String sponsor;

    @Column(name="SH_QNAME")
    private String qName;

    @Column(name="SH_EXDATE")
    private LocalDate examDate;

    @Column(name="SH_RESULTS")
    private String results;

    @Column(name="SH_COST")
    private Integer cost;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "SH_SHAINID")
    @JsonIgnore
    private EmployeeMaster employee;


}
