package aimyamaguchi.co.jp.aimspringsql.employee.Repositories;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.RecruitTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<RecruitTypeModel, Long> {
}
