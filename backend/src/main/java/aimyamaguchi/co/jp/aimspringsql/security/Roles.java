package aimyamaguchi.co.jp.aimspringsql.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="[M_ROLES]", schema="[dbo]")
public class Roles implements GrantedAuthority{


    private static final long serialVersionUID = 1L;
    @Id
    private long roleid;
    private String roledesc;

    @Override
    public String getAuthority() {
        return "ROLE_" +this.getRoledesc();
    }

}