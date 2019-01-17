package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeMin {

    private String id;
    private String fullName;
    private Set<AFFILIATIONData> group;
    private boolean admin;

}
