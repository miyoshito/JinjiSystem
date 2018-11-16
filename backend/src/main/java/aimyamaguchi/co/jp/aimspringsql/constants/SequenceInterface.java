package aimyamaguchi.co.jp.aimspringsql.constants;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceInterface extends JpaRepository<Sequences, String>{
        
        Sequences findBySeqTablename(String name);
    
}