package aimyamaguchi.co.jp.aimspringsql.employee.Repositories;

import java.util.List;
import java.util.function.Predicate;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface EmployeeRepository extends JpaRepository<EmployeeMaster, String>, QueryByExampleExecutor<EmployeeMaster>, QuerydslPredicateExecutor {

    EmployeeMaster findByShainId(String shainid);

    List<EmployeeMaster> findAll();

    List<EmployeeMaster> findByShainIdIn(List<String> list);



}