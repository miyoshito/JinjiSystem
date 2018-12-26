package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.MAKERData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakerRepository extends JpaRepository<MAKERData, Long> {
}
