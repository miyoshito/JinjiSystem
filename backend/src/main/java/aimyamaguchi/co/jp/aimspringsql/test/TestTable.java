package aimyamaguchi.co.jp.aimspringsql.test;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "teste")
@Entity
@Data
public class TestTable{

    @Id
    private Long userid;

    private String username;

    private String password;

}