package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMin {

    private String id;
    private String fullName;
    private Set<AFFILIATIONData> group;
    private boolean admin;

}
