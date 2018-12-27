package aimyamaguchi.co.jp.aimspringsql.education;

import aimyamaguchi.co.jp.aimspringsql.util.CustomValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudyCourseService {

    @Autowired
    StudyCourseInterface sci;

    @Autowired
    EntityManager em;

    @Autowired
    CustomValidators valid;


    public boolean insertSCAttempt(StudyCourseModel scm){
        LocalDate now = LocalDate.now();
                scm.setUpdated(now);
                sci.save(scm);
                return true;
    }

    public Optional<StudyCourseModel> findSCM(String id){
        return sci.findById(Long.valueOf(id));
    }

    public List<String> searchSC(String id, String name, String kana, String sponsor, Integer expenses, String start, String end, String biOp){

        ArrayList<String> queryParam = new ArrayList<>();
        String operator = "=";

        if(valid.isNullValidator(biOp) && biOp.equals("grt")) operator = ">";
        if(valid.isNullValidator(biOp) && biOp.equals("lst")) operator = "<";



        if(valid.isNullValidator(id))queryParam.add("sha.sha_no = "+id);
        if(valid.isNullValidator(name))queryParam.add("sha.sha_name like '%"+name+"%'");
        if(valid.isNullValidator(kana))queryParam.add("sha.sha_kana like '%"+kana+"%'");
        if(valid.isNullValidator(sponsor))queryParam.add("ky.sponsor like '%"+sponsor+"%'");
        if(valid.isNullValidator(expenses))queryParam.add("ky.hotel_expenses + ky.tuition_fee + ky.transport_expenses " +operator+" "+expenses);
        if(valid.isNullValidator(start))queryParam.add("ky.start_period > '"+start+ "'");
        if(valid.isNullValidator(end))queryParam.add("ky.start_period < '"+end+ "'");

        String param = String.join(" and \n", queryParam);

        Query query = em.createNativeQuery("" +
                "SELECT DISTINCT\n" +
                "sha.sha_no\n" +
                "FROM m_shain sha left outer join m_kyoiku ky on sha.sha_no = ky.employee_id\n" +
                "where\n" + param);

        return query.getResultList();
    }

}
