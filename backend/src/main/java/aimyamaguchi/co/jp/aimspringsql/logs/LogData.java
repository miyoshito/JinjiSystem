package aimyamaguchi.co.jp.aimspringsql.logs;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "[M_LOGS]", schema = "[DBO]")
public class LogData {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LOG_ID")
    private Long id;

    @Column(name = "TABLE_ALIAS")
    private String tableAlias;

    @Column(name= "UPDATED_ID")
    private String updatedId;

    @Column(name = "UPDATED_COLUMN")
    private String updatedColumn;

    @Column(name= "OLD_VALUE")
    private String oldValue;

    @Column(name= "NEW_VALUE")
    private String newValue;

    @Column(name= "UPDATED_BY")
    private String updatedBy;

    @Column(name= "UPDATED_DATE")
    private LocalDateTime updatedDate;



}
