package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmployeeRepository extends JpaRepository<EmployeeMaster, String>, QueryByExampleExecutor<EmployeeMaster> {

    EmployeeMaster findByShainId(String shainid);

    List<EmployeeMaster> findAll();

    List<EmployeeMaster> findByShainIdIn(List<String> list);

}