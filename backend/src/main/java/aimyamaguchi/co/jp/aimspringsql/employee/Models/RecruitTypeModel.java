package aimyamaguchi.co.jp.aimspringsql.employee.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "[m_saiyoukubun]")
public class RecruitTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECRUIT_ID")
    private Long id;

    @Column(name = "RECRUIT_DESC", length = 60)
    private String desc;

    @Column
    private boolean active;

    @OneToMany(mappedBy = "shainRecruit")
    @JsonIgnore
    private Set<EmployeeMaster> employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<EmployeeMaster> getEmployee() {
        return employee;
    }

    public void setEmployee(Set<EmployeeMaster> employee) {
        this.employee = employee;
    }
}
