package aimyamaguchi.co.jp.aimspringsql.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyCourseInterface extends JpaRepository<StudyCourseModel, Long> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT\n" +
            "sha.sha_no\n" +
            "FROM m_shain sha left outer join m_kyoiku ky on sha.sha_no = ky.employee_id\n" +
            "where\n" +
            "coalesce(sha.sha_no, '') like '%' :id '%'\n" +
            "and coalesce (sha.sha_kana, '') like '%' :name '%'\n" +
            "and coalesce(sha.sha_name, '') like '%' :kana '%'\n" +
            "and coalesce(ky.sponsor,'') like '%' :sponsor '%'\n" +
            "and coalesce(ky.hotel_expenses + ky.tuition_fee + ky.transport_expenses,0) :operator = :experience\n" +
            "and coalesce (ky.start_period,'') > :start \n" +
            "and coalesce (ky.end_period,'') < :end\n")
    List<String> findByParams
    (
     @Param("id") String id,
     @Param("kana") String kana,
     @Param("name") String name,
     @Param("sponsor") String sponsor,
     @Param("operator") String operator,
     @Param("experience") Integer experience,
     @Param("start") String start,
     @Param("end") String end
    );
}
