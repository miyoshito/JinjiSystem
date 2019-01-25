package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="m_kuruma")
public class CARMODELData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CAR_ID")
    private Long id;
    @Column(name="car_desc")
    private String desc;
    @Column(name="active")
    private boolean active;

    @OneToMany(mappedBy = "shainCarModel")
    @JsonIgnore
    private List<EmployeeMaster> employee;
}
