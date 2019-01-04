package aimyamaguchi.co.jp.aimspringsql.employee.Repositories;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.CARMODELData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarmodelRepository extends JpaRepository<CARMODELData, Long> {
}
