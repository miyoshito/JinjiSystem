package aimyamaguchi.co.jp.aimspringsql.constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name="[vw_data]", schema="[dbo]")
@NoArgsConstructor
public class DataModel {

    private String name;

    private int id;
    @Id
    private String tdesc;

    private boolean active;

}