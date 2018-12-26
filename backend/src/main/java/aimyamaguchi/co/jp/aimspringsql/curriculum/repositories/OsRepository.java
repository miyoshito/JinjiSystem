package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.OSData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsRepository extends JpaRepository<OSData, Long> {
}
