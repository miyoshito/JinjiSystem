package aimyamaguchi.co.jp.aimspringsql;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdministratorModel {

    private String id;

    private String name;

    private String areas;

    private boolean admin;

}
