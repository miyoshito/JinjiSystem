package aimyamaguchi.co.jp.aimspringsql.employee.Models;

import java.io.Serializable;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.*;

import aimyamaguchi.co.jp.aimspringsql.education.StudyCourseModel;
import aimyamaguchi.co.jp.aimspringsql.qualifications.QualificationsModel;
import aimyamaguchi.co.jp.aimspringsql.security.AuditableModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="M_SHAIN")

public class EmployeeMaster extends AuditableModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="SHA_NO", length=6, nullable=false)
    private String shainId;

    @Column(name="SHA_PASSWORD", length=100, nullable=false)
    private String shainPassword;

    @Column(name="SHA_NAME", length=60, nullable=false)
    private String shainName;

    @Column(name="SHA_KANA", length=60, nullable=false)
    private String shainKana;

    @Column(name="SHA_BIRTHDAY", nullable=false)
    private Date shainBirthday;

    @Column(name="SHA_BLOOD", length=10)
    private String shainBloodType;

    @Column(name="SHA_SEX", length=10, nullable=false)
    private String shainSex;

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

    @Column(name="SHA_ENTRYDAY", nullable=false)
    private Date shainJoinedDate;
    
    @Column(name="SHA_RETIREDAY")
    private Date shainRetiredDate;

    @Column(name="SHA_RETIREFLG", nullable=false)
    private boolean shainRetired;

    @Column(name="SHA_ADMIN")
    private boolean admin;

    @Column(name="SHA_NOTES", length=200)
    private String shainNotes;

    @Column(name="SHA_DELETEFLG")
    private boolean shainDeletedFlag;

    @JoinColumn(name="SHA_CARMODEL")
    @ManyToOne
    private CARMODELData shainCarModel;

    @ManyToOne
    @JoinColumn(name="SHA_POSITION")
    private POSITIONData position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHA_RECRUIT", nullable=false)
    private RecruitTypeModel shainRecruit;

    @ManyToOne
    @JoinColumn(name="SHA_AVALIABLEAREA")
    private WorkAreaData shainArea;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="SHA_SHOZOKU")
    private Set<AFFILIATIONData> affiliation;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="SHA_RIREKI")
    private ResumeModel resume;

    @OneToMany(mappedBy = "employee_id", fetch = FetchType.LAZY)
    @OrderBy("startdate DESC")
    private List<CurriculumModel> curriculum;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<QualificationsModel> qualifications = new HashSet<>();

    @OneToMany(mappedBy ="employee", fetch = FetchType.LAZY)
    private Set<StudyCourseModel> educations  = new HashSet<>();

    @Column(name = "SHA_EXPERIENCETIME")
    private Integer totalExperienceTime;


    public Integer getTotalExperienceTime(){

        final Integer[] tt = {0};
        if (this.getCurriculum().size() > 0) {

            //getCurriculum sempre vai trazer por DESC no comeco


            Integer minYear;
            Integer maxYear;



            minYear = this.getCurriculum()
                    .stream()
                    .map(sy -> sy.getStartdate().getYear())
                    .min(Integer::compareTo).get();
            maxYear = this.getCurriculum()
                    .stream()
                    .map(my -> my.getEnddate().getYear())
                    .max(Integer::compareTo).get();

            this.getCurriculum().stream().
                    forEach(cv -> {
                        Period period = Period.between(cv.getStartdate().withDayOfMonth(1).toLocalDate(), cv.getEnddate().withDayOfMonth(1).toLocalDate());
                        if (period.getYears() > 0) {
                            tt[0] += period.getMonths() + (period.getYears() * 12);
                        } else {
                            tt[0] += period.getMonths();
                        }

                    });
        }
        this.totalExperienceTime = tt[0];
        return tt[0];

    }

    public void setTotalExperienceTime(Integer experience){
        this.totalExperienceTime = experience;
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

    public String getShainNotes() {
        return shainNotes;
    }

    public void setShainNotes(String shainNotes) {
        this.shainNotes = shainNotes;
    }

    public boolean isShainDeletedFlag() {
        return shainDeletedFlag;
    }

    public void setShainDeletedFlag(boolean shainDeletedFlag) {
        this.shainDeletedFlag = shainDeletedFlag;
    }

    @JsonManagedReference
    public ResumeModel getResume() {
        return resume;
    }

    public void setResume(ResumeModel resume) {
            this.resume = resume;
    }

    public List<CurriculumModel> getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(List<CurriculumModel> curriculum) {
        this.curriculum = curriculum;
    }

    public Set<StudyCourseModel> getEducations() {
        return this.educations;
    }

    public void setEducations(Set<StudyCourseModel> educations) {
        this.educations = educations;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<QualificationsModel> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Set<QualificationsModel> qualifications) {
        this.qualifications = qualifications;
    }
}