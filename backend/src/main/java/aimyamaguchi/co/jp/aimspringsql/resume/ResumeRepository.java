package aimyamaguchi.co.jp.aimspringsql.resume;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<ResumeModel, Long> {

    ResumeModel findByEmployee(EmployeeMaster employee);

}
