package aimyamaguchi.co.jp.aimspringsql.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmployeeRepository extends JpaRepository<EmployeeMaster, String>{

    EmployeeMaster findByShainId(String shainid);

    List<EmployeeMaster> findAll();

    @Query(nativeQuery = true, value =
            "select *\n" +
            "from [dbo].[m_shain]\n" +
            "inner join [dbo].[m_rirekisho]\n" +
            "on m_rirekisho.sha_no=m_shain.sha_no\n" +
            "\n" +
            "left outer join [dbo].m_keireki keireki_\n" +
            "on m_rirekisho.ri_id = keireki_.rk_resume\n" +
            "\n" +
            "left outer join [dbo].m_shikaku shikaku_\n" +
            "on m_rirekisho.ri_id = shikaku_.rs_resume\n" +
            "\n" +
            "left outer join [dbo].m_hyosho hyosho_\n" +
            "on m_rirekisho.ri_id = hyosho_.rh_resume\n"+
            "where\n" +
            "m_rirekisho.sha_no = :id or\n" +
            "m_shain.sha_name like :name%\n" +
            "m_shain.sha_kana like :kana% or\n" +
            "m_shain.sha_recruit = :recruit OR\n" +

            "m_shain.sha_birthday = :age OR\n" +
            "m_rirekisho.ri_universityname = :university OR\n" +

            "keireki_.RK_SCHOOL_WORK like :career% OR\n" +
            "shikaku_.rs_qualification like :qualification% OR\n" +
            "hyosho_.rh_contents like :commendation% ")
    List<EmployeeMaster> resumeSearch(
            @Param("id") String shain_id,
            @Param("name") String shain_name,
            @Param("kana") String shain_kana,
            @Param("recruit") String shain_recruit,
            @Param("age") Long shain_birthday,
            @Param("university") String resume_univ,
            @Param("career") String career_desc,
            @Param("qualification") String qualification_desc,
            @Param("commendation") String commendation_desc
    );

}