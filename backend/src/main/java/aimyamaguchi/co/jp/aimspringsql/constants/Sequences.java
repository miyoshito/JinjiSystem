package aimyamaguchi.co.jp.aimspringsql.constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/*create table m_sequences(
seq_tablename varchar(30),
seq_value bigint,
primary key(seq_tablename)
);*/

@Data
@Entity
@Table(name="m_sequences")
public class Sequences {

    @Id //evitando um NPE idiota do java...
    @Column(name="_id")
    private Long _id;

    @Column(name="seq_tablename")
    private String seqTablename;

    @Column(name="seq_value")
    private String seqValue;

}