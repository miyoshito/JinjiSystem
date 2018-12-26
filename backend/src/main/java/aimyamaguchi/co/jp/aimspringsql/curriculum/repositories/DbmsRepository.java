package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.DBMSData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbmsRepository extends JpaRepository<DBMSData, Long> {
}
