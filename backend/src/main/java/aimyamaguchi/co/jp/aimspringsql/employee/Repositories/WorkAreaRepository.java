package aimyamaguchi.co.jp.aimspringsql.employee.Repositories;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.WorkAreaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkAreaRepository extends JpaRepository<WorkAreaData, Long> {
}
