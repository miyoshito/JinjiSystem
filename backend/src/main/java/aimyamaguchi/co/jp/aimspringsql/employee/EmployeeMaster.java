package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import aimyamaguchi.co.jp.aimspringsql.constants.AFFILIATIONData;
import aimyamaguchi.co.jp.aimspringsql.constants.DataModel;
import aimyamaguchi.co.jp.aimspringsql.constants.POSITIONData;
import aimyamaguchi.co.jp.aimspringsql.constants.WorkAreaData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.security.Roles;
import lombok.Data;

@Data
@Entity
@Table(name="[M_SHAIN]", schema="[DBO]")
public class EmployeeMaster implements UserDetails{

    private static final long serialVersionUID = 1L;


	public EmployeeMaster(){}

    @Id
    @Column(name="SHA_NO", length=6, nullable=false)
    private String shainId;

    @Column(name="SHA_PASSWORD", length=100, nullable=false)
    private String shainPassword;

    @Column(name="SHA_NAME", length=20, nullable=false)
    private String shainName;

    @Column(name="SHA_RECRUIT", length=20, nullable=false)
    private String shainRecruit; //selectable key (hardcoded)

    @Column(name="SHA_KANA", length=20, nullable=false)
    private String shainKana;

    @Column(name="SHA_BIRTHDAY", nullable=false)
    private Date shainBirthday;

    @Column(name="SHA_BLOOD", length=5)
    private String shainBloodType; //selectable key (hardcoded)

    @Column(name="SHA_SEX", length=1, nullable=false)
    private char shainSex; //selectable key (hardcoded)

    @ManyToOne
    @JoinColumn(name="SHA_AFFILIATION")
    private AFFILIATIONData affiliation;

    @ManyToOne
    @JoinColumn(name="SHA_POSITION")
    private POSITIONData position;
    
    @Column(name="SHA_SUPPORT", length=1, nullable=false)
    private boolean shainSupport;

    @Column(name="SHA_MARRIED", length=1, nullable=false)
    private boolean shainMarried;

    @Column(name="SHA_HOMEPHONENUMBER", length=13)
    private String shainHomePhoneNumber;

    @Column(name="SHA_MOBILEPHONENUMBER", length=13)
    private String shainMobilePhoneNumber;

    @Column(name="SHA_MAIL", length=30)
    private String shainMail;

    @Column(name="SHA_MOBILEMAIL", length=30, nullable=false)
    private String shainMobileMail;

    @Column(name="SHA_POSTALCODE", length=8, nullable=false)
    private String shainPostalCode;

    @Column(name="SHA_ADDRESS", length=30, nullable=false)
    private String shainAddress;

    //@Column(name="SHA_AVALIABLEAREA", length=30, nullable=false)
    @ManyToOne
    @JoinColumn(name="SHA_AVALIABLEAREA")
    private WorkAreaData shainArea;


    @Column(name="SHA_ENTRYDAY", nullable=false)
    private Date shainJoinedDate;

    
    @Column(name="SHA_RETIREDAY")
    private Date shainRetiredDate;

    @Column(name="SHA_RETIREFLG", nullable=false)
    private boolean shainActive;

    @Column(name="SHA_CARDMODEL", length=30)
    private String shainCarModel;

    @JoinColumn(name="SHA_AUTHFLAG")
    @ManyToOne
    private Roles role;

    //@Column(name="SHA_SOUMFLG", length=1)
    //@Column(name="SHA_ADMINFLG", length=1)   

    @Column(name="SHA_NOTES", length=50)
    private String shainNotes;

    @Column(name="SHA_RESIST")
    private Date shainRegisterDate;

    @Column(name="SHA_RESISTER", length=10)
    private String shainRegisteredBy; //userid

    @Column(name="SHA_DELETEFLG", length=1)
    private boolean shainDeletedFlag;

    @OneToMany(mappedBy="employee")
    private List<ResumeModel> resumes;

    @OneToMany(mappedBy = "employee")
    private List<CurriculumModel> curriculum;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.getShainPassword();
    }

    @Override
    public String getUsername() {
        return this.getShainId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
		return this.isShainActive();
	}
    

}