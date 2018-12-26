package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.ASSIGNData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignRepository extends JpaRepository<ASSIGNData, Long> {
}
