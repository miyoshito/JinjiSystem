package aimyamaguchi.co.jp.aimspringsql.constants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;

public interface DataInterface extends JpaRepository<DataModel, String>{
        @Query(nativeQuery=true, value="select * from vw_data")
        List<DataModel> getAll();
    
}