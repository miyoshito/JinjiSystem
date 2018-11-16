package aimyamaguchi.co.jp.aimspringsql.constants;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="[vw_shaData]", schema="[dbo]")
public class EmployeeParamModel {
    
    private String tname;

    private int tid;

    @Id
    private String tdesc;

    private boolean active;    

}