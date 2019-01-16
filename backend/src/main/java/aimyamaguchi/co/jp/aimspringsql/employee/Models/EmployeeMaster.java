package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.security.Roles;


@Entity
@Table(name="[M_SHAIN]", schema="[DBO]")

public class EmployeeMaster implements UserDetails, Serializable{

    private static final long serialVersionUID = 1L;

    @Transient
    private PersistentAttributeInterceptor persistentAttributeInterceptor;

    @Id
    @Column(name="SHA_NO", length=6, nullable=false)
    private String shainId;

    @Column(name="SHA_PASSWORD", length=100, nullable=false)
    private String shainPassword;

    @Column(name="SHA_NAME", length=60, nullable=false)
    private String shainName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHA_RECRUIT", nullable=false)
    private RecruitTypeModel shainRecruit;

    @Column(name="SHA_KANA", length=60, nullable=false)
    private String shainKana;

    @Column(name="SHA_BIRTHDAY", nullable=false)
    private Date shainBirthday;

    @Column(name="SHA_BLOOD", length=10)
    private String shainBloodType;

    @Column(name="SHA_SEX", length=10, nullable=false)
    private String shainSex;

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

    @Column (name="SHA_LASTUPDATE")
    private LocalDate shainLastUpdated;

    @Column (name="SHA_UPDATEDBY")
    private String shainUpdatedBy;

    @Column(name="SHA_DELETEFLG")
    private boolean shainDeletedFlag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="SHA_SHOZOKU")
    private Set<AFFILIATIONData> affiliation;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="SHA_RIREKI")
    private ResumeModel resume;

    @OneToMany(mappedBy = "employee_id", fetch = FetchType.LAZY)
    @OrderBy("enddate DESC")
    private List<CurriculumModel> curriculum;

    @OneToMany(mappedBy ="employee", fetch = FetchType.LAZY)
    private Set<StudyCourseModel> educations;

    @Column(name = "SHA_EXPERIENCETIME")
    private Integer totalExperienceTime;

    public Integer getTotalExperienceTime(){
        return this.totalExperienceTime;
    }

    public void setTotalExperienceTime(){

        final Integer[] tt = {0};
        if (this.getCurriculum().size() > 0) {
            this.getCurriculum().stream().forEach(cv -> {
                Period period = Period.between(cv.getStartdate().withDayOfMonth(1), cv.getEnddate().withDayOfMonth(1));
                if (period.getYears() > 0) {
                    tt[0] += period.getMonths() + (period.getYears() * 12);
                } else {
                    tt[0] += period.getMonths();
                }

            });
        }
        this.totalExperienceTime = tt[0];
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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


    public String getShainId() {
        return shainId;
    }

    public void setShainId(String shainId) {
        this.shainId = shainId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getShainPassword() {
        return shainPassword;
    }

    public void setShainPassword(String shainPassword) {
        this.shainPassword = shainPassword;
    }

    public String getShainName() {
        return shainName;
    }

    public void setShainName(String shainName) {
        this.shainName = shainName;
    }

    public RecruitTypeModel getShainRecruit() {
        return shainRecruit;
    }

    public void setShainRecruit(RecruitTypeModel shainRecruit) {
        this.shainRecruit = shainRecruit;
    }

    public String getShainKana() {
        return shainKana;
    }

    public void setShainKana(String shainKana) {
        this.shainKana = shainKana;
    }

    public Date getShainBirthday() {
        return shainBirthday;
    }

    public void setShainBirthday(Date shainBirthday) {
        this.shainBirthday = shainBirthday;
    }

    public String getShainBloodType() {
        return shainBloodType;
    }

    public void setShainBloodType(String shainBloodType) {
        this.shainBloodType = shainBloodType;
    }

    public String getShainSex() {
        return shainSex;
    }

    public void setShainSex(String shainSex) {
        this.shainSex = shainSex;
    }

    public Set<AFFILIATIONData> getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Set<AFFILIATIONData> affiliation) {
        this.affiliation = affiliation;
    }

    public POSITIONData getPosition() {
        return position;
    }

    public void setPosition(POSITIONData position) {
        this.position = position;
    }

    public boolean isShainSupport() {
        return shainSupport;
    }

    public void setShainSupport(boolean shainSupport) {
        this.shainSupport = shainSupport;
    }

    public boolean isShainMarried() {
        return shainMarried;
    }

    public void setShainMarried(boolean shainMarried) {
        this.shainMarried = shainMarried;
    }

    public String getShainHomePhoneNumber() {
        return shainHomePhoneNumber;
    }

    public void setShainHomePhoneNumber(String shainHomePhoneNumber) {
        this.shainHomePhoneNumber = shainHomePhoneNumber;
    }

    public String getShainMobilePhoneNumber() {
        return shainMobilePhoneNumber;
    }

    public void setShainMobilePhoneNumber(String shainMobilePhoneNumber) {
        this.shainMobilePhoneNumber = shainMobilePhoneNumber;
    }

    public String getShainMail() {
        return shainMail;
    }

    public void setShainMail(String shainMail) {
        this.shainMail = shainMail;
    }

    public String getShainMobileMail() {
        return shainMobileMail;
    }

    public void setShainMobileMail(String shainMobileMail) {
        this.shainMobileMail = shainMobileMail;
    }

    public String getShainPostalCode() {
        return shainPostalCode;
    }

    public void setShainPostalCode(String shainPostalCode) {
        this.shainPostalCode = shainPostalCode;
    }

    public String getShainAddress() {
        return shainAddress;
    }

    public void setShainAddress(String shainAddress) {
        this.shainAddress = shainAddress;
    }

    public WorkAreaData getShainArea() {
        return shainArea;
    }

    public void setShainArea(WorkAreaData shainArea) {
        this.shainArea = shainArea;
    }

    public Date getShainJoinedDate() {
        return shainJoinedDate;
    }

    public void setShainJoinedDate(Date shainJoinedDate) {
        this.shainJoinedDate = shainJoinedDate;
    }

    public Date getShainRetiredDate() {
        return shainRetiredDate;
    }

    public void setShainRetiredDate(Date shainRetiredDate) {
        this.shainRetiredDate = shainRetiredDate;
    }

    public boolean isShainRetired() {
        return shainRetired;
    }

    public void setShainRetired(boolean shainRetired) {
        this.shainRetired = shainRetired;
    }

    public CARMODELData getShainCarModel() {
        return shainCarModel;
    }

    public void setShainCarModel(CARMODELData shainCarModel) {
        this.shainCarModel = shainCarModel;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getShainNotes() {
        return shainNotes;
    }

    public void setShainNotes(String shainNotes) {
        this.shainNotes = shainNotes;
    }

    public Date getShainRegisterDate() {
        return shainRegisterDate;
    }

    public void setShainRegisterDate(Date shainRegisterDate) {
        this.shainRegisterDate = shainRegisterDate;
    }

    public String getShainRegisteredBy() {
        return shainRegisteredBy;
    }

    public void setShainRegisteredBy(String shainRegisteredBy) {
        this.shainRegisteredBy = shainRegisteredBy;
    }

    public boolean isShainDeletedFlag() {
        return shainDeletedFlag;
    }

    public void setShainDeletedFlag(boolean shainDeletedFlag) {
        this.shainDeletedFlag = shainDeletedFlag;
    }

    @JsonManagedReference
    public ResumeModel getResume() {
        /*if(persistentAttributeInterceptor != null){
            return (ResumeModel) persistentAttributeInterceptor.readObject(this, "resume", resume);
        }*/
        return resume;
    }

    public void setResume(ResumeModel resume) {
        /*
        resume.setEmployee(this);

        if (persistentAttributeInterceptor != null) {
            this.resume = (ResumeModel) persistentAttributeInterceptor.writeObject(this, "resume", resume, resume);
        } else {*/
            this.resume = resume;
    }

    public List<CurriculumModel> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<CurriculumModel> curriculum) {
        this.curriculum = curriculum;
    }

    public Set<StudyCourseModel> getEducations() {
        return educations;
    }

    public void setEducations(Set<StudyCourseModel> educations) {
        this.educations = educations;
    }

    public LocalDate getShainLastUpdated() {
        return shainLastUpdated;
    }

    public void setShainLastUpdated(LocalDate shainLastUpdated) {
        this.shainLastUpdated = shainLastUpdated;
    }

    public String getShainUpdatedBy() {
        return shainUpdatedBy;
    }

    public void setShainUpdatedBy(String shainUpdatedBy) {
        this.shainUpdatedBy = shainUpdatedBy;
    }
}