package aimyamaguchi.co.jp.aimspringsql.employee.Repositories;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.POSITIONData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<POSITIONData, Long> {
}
