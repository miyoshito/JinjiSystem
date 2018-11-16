package aimyamaguchi.co.jp.aimspringsql.constants;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aimyamaguchi.co.jp.aimspringsql.curriculum.CurriculumModel;
import lombok.Data;

@Data
@Entity
@Table(name="[M_RESPONSE]", schema="[dbo]")
public class RESPONSEData {
    
    @Id
    @Column(name="RESPONSE_ID")
    private Long id;
    @Column(name="RESPONSE_DESC")
    private String desc;
    @Column(name="ACTIVE")
    private boolean active;
    

    @ManyToMany(mappedBy="responseData")
    @JsonIgnore
    private List<CurriculumModel> curriculum;
    
}