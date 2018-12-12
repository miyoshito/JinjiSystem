package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmployeeRepository extends JpaRepository<EmployeeMaster, String>, QueryByExampleExecutor<EmployeeMaster> {

    EmployeeMaster findByShainId(String shainid);

    List<EmployeeMaster> findAll();

    List<EmployeeMaster> findByShainIdIn(List<String> list);


    @Query(nativeQuery = true,
    value = "SELECT [sha_no]\n" +
            "      ,[sha_retireflg]\n" +
            "      ,[sha_address]\n" +
            "      ,[sha_birthday]\n" +
            "      ,[sha_blood]\n" +
            "      ,[sha_deleteflg]\n" +
            "      ,[sha_homephonenumber]\n" +
            "      ,[sha_entryday]\n" +
            "      ,[sha_kana]\n" +
            "      ,[sha_mail]\n" +
            "      ,[sha_married]\n" +
            "      ,[sha_mobilemail]\n" +
            "      ,[sha_mobilephonenumber]\n" +
            "      ,[sha_name]\n" +
            "      ,[sha_notes]\n" +
            "      ,[sha_password]\n" +
            "      ,[sha_postalcode]\n" +
            "      ,[sha_recruit]\n" +
            "      ,[sha_resist]\n" +
            "      ,[sha_resister]\n" +
            "      ,[sha_retireday]\n" +
            "      ,[sha_sex]\n" +
            "      ,[sha_support]\n" +
            "      ,[sha_position]\n" +
            "      ,[sha_authflag]\n" +
            "      ,[sha_avaliablearea]\n" +
            "      ,[sha_carmodel]\n" +
            "\t  ,(SELECT (SUM(DATEDIFF(MONTH,sho.cv_start, sho.cv_end))) as experienceTime from m_shokumukeireki sho where sho.sha_no = m_shain.sha_no)\n" +
            "  FROM [jinji_dev].[dbo].[m_shain]" +
            "")
    List<EmployeeMaster> findAllPlusExperience();
}