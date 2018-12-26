package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.TOOLSData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolsRepository extends JpaRepository<TOOLSData, Long> {
}
