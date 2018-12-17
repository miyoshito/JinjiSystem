package aimyamaguchi.co.jp.aimspringsql.curriculum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryDataRepository extends JpaRepository<INDUSTRYData, Long> {

    List<INDUSTRYData> findAll();


}
