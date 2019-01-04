package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSearchFunctions {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeMaster> searchResults(String id, String name, String kana, List<String> aff){
        ArrayList<String> queryParam = new ArrayList<>();
        Optional.ofNullable(id).ifPresent((p) -> {if (!p.equals("")) queryParam.add("m.sha_no = "+p+" and");});
        Optional.ofNullable(name).ifPresent((p) -> {if (!p.equals("")) queryParam.add("m.sha_name like '%"+p+"%' and");});
        Optional.ofNullable(kana).ifPresent((p) -> {if (!p.equals("")) queryParam.add("m.sha_kana like '%"+p+"%' and");});

        if(aff.size() > 0) {
            String in = String.join(",", aff);
            queryParam.add("sho.affiliation_affiliation_id in (" + in + ") and");
        }
        String param = String.join(" ", queryParam);
        Query query = entityManager.createNativeQuery(
                "select distinct\n"
                        +"m.sha_no\n"
                        +"from\n"
                        +"m_shain m full join sha_shozoku sho on\n"
                        +"m.sha_no = sho.employee_sha_no\n"
                        +"where\n "
                        + param.substring(0, param.length() -4)
        );
        List<String> results = query.getResultList();
        if(results.size() > 0){
            return employeeRepository.findByShainIdIn(results);
        }
        else return null;
    }

}
