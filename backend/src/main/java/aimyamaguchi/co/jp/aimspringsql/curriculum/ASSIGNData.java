package aimyamaguchi.co.jp.aimspringsql.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="[M_ASSIGN]", schema="[dbo]")
public class ASSIGNData {

        @Id
        @Column(name="ASSIGN_ID")
        private Long id;
        @Column(name="ASSIGN_DESC")
        private String desc;
        @Column(name="ACTIVE")
        private boolean active;

        @OneToMany(mappedBy="assignData")
        @JsonIgnore
        private List<CurriculumModel> curriculum;

    }
