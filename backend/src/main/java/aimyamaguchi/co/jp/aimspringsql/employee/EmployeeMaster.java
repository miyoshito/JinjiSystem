package aimyamaguchi.co.jp.aimspringsql.employee;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.security.Roles;
import lombok.Data;

@Data
@Entity
@ToString
@Table(name="[M_SHAIN]", schema="[DBO]")
public class EmployeeMaster implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="SHA_NO", length=6, nullable=false)
    private String shainId;
    @Column(name="SHA_PASSWORD", length=100, nullable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String shainPassword;

    @Column(name="SHA_NAME", length=60, nullable=false)
    private String shainName;

    @Column(name="SHA_RECRUIT", length=20, nullable=false)
    private String shainRecruit; //selectable key (hardcoded)

    @Column(name="SHA_KANA", length=60, nullable=false)
    private String shainKana;

    @Column(name="SHA_BIRTHDAY", nullable=false)
    private Date shainBirthday;

    @Column(name="SHA_BLOOD", length=10)
    private String shainBloodType; //selectable key (hardcoded)

    @Column(name="SHA_SEX", length=10, nullable=false)
    private String shainSex; //selectable key (hardcoded)

    @ManyToMany
    @JoinTable(name="SHA_SHOZOKU")
    private List<AFFILIATIONData> affiliation;

    @ManyToOne
    @JoinColumn(name="SHA_POSITION")
    private POSITIONData position;
    
    @Column(name="SHA_SUPPORT", nullable=false)
    private boolean shainSupport;

    @Column(name="SHA_MARRIED", nullable=false)
    private boolean shainMarried;

    @Column(name="SHA_HOMEPHONENUMBER", length=15)
    private String shainHomePhoneNumber;

    @Column(name="SHA_MOBILEPHONENUMBER", length=15)
    private String shainMobilePhoneNumber;

    @Column(name="SHA_MAIL", length=60)
    private String shainMail;

    @Column(name="SHA_MOBILEMAIL", length=60, nullable=false)
    private String shainMobileMail;

    @Column(name="SHA_POSTALCODE", length=10, nullable=false)
    private String shainPostalCode;

    @Column(name="SHA_ADDRESS", length=100, nullable=false)
    private String shainAddress;

    @ManyToOne
    @JoinColumn(name="SHA_AVALIABLEAREA")
    private WorkAreaData shainArea;

    @Column(name="SHA_ENTRYDAY", nullable=false)
    private Date shainJoinedDate;
    
    @Column(name="SHA_RETIREDAY")
    private Date shainRetiredDate;

    @Column(name="SHA_RETIREFLG", nullable=false)
    private boolean shainRetired;

    @JoinColumn(name="SHA_CARMODEL")
    @ManyToOne
    private CARMODELData shainCarModel;

    @JoinColumn(name="SHA_AUTHFLAG")
    @ManyToOne
    private Roles role;

    @Column(name="SHA_NOTES", length=200)
    private String shainNotes;

    @Column(name="SHA_RESIST")
    private Date shainRegisterDate;

    @Column(name="SHA_RESISTER", length=10)
    private String shainRegisteredBy; //userid

    @Column(name="SHA_DELETEFLG")
    private boolean shainDeletedFlag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SHA_RESUME", nullable=true)
    private ResumeModel resume;

    @OneToMany(mappedBy = "employee_id", fetch = FetchType.LAZY)
    private List<CurriculumModel> curriculum;

    @OneToMany(mappedBy ="employee", fetch = FetchType.LAZY)
    private List<StudyCourseModel> educations;

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
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
		return !this.isShainRetired();
	}
    

}