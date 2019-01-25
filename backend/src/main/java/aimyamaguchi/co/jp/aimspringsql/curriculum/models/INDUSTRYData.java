package aimyamaguchi.co.jp.aimspringsql.curriculum.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="M_GYOUMU")
@Data
public class INDUSTRYData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="INDUSTRY_TYPE_ID")
    private Long id;
    @Column(name="INDUSTRY_TYPE_DESC")
    private String tdesc;
    @Column(name="ACTIVE")
    private boolean active;
    @OneToMany(mappedBy = "id.industryid", fetch = FetchType.EAGER)
    private List<INDCLASSIFICATIONData> industryClass = new ArrayList<>();

}


