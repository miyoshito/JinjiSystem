package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.INDUSTRYData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryDataRepository extends JpaRepository<INDUSTRYData, Long> {

    List<INDUSTRYData> findAll();


}
