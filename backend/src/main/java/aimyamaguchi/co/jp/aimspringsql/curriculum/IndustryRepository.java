package aimyamaguchi.co.jp.aimspringsql.curriculum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<INDCLASSIFICATIONData, IndustryKeys> {

    INDCLASSIFICATIONData findOneById(IndustryKeys e);
    

}
