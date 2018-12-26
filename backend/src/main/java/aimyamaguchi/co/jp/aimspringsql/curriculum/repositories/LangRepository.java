package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.LANGData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LangRepository extends JpaRepository<LANGData, Long> {
}
