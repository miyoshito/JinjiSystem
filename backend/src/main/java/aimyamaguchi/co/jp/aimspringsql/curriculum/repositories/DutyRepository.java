package aimyamaguchi.co.jp.aimspringsql.curriculum.repositories;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.DUTYData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyRepository extends JpaRepository<DUTYData, Long> {

}
