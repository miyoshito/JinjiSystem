package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeMaster, String>{

    EmployeeMaster findByShainId(String shainid);

    List<EmployeeMaster> findAll();

}