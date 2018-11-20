package aimyamaguchi.co.jp.aimspringsql.files;

import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;


@Table(name="F_KIREKISHO")
@Entity
@Data
public class ResumeFileDetails {

    @Id
    private Long fileid;

    @Transient
    private MultipartFile document;

    private String filepath;

    private String title;

    @ManyToOne
    @JoinColumn(name="resume_id")
    private ResumeModel resume;

}
