package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDCLASSIFICATIONData;
import aimyamaguchi.co.jp.aimspringsql.curriculum.models.IndustryKeys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryRepository extends JpaRepository<INDCLASSIFICATIONData, IndustryKeys> {

    INDCLASSIFICATIONData findOneById(IndustryKeys e);
    

}
