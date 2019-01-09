package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import aimyamaguchi.co.jp.aimspringsql.constants.SequenceInterface;
import aimyamaguchi.co.jp.aimspringsql.constants.Sequences;
import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeModel;
import aimyamaguchi.co.jp.aimspringsql.resume.ResumeRepository;
import aimyamaguchi.co.jp.aimspringsql.util.SearchFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class EmployeeInsertFunctions {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SequenceInterface seq;
    @Autowired
    private SearchFilters sf;


    public void insertEmployee(EmployeeMaster employee, HttpServletRequest reqs){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String token = reqs.getHeader("authorization");

        if (!employee.getShainId().equals("") && employeeRepository.findByShainId(employee.getShainId()) != null){
            employee.setResume(sf.getEmployeeWithResume(employee.getShainId()).getResume());
            employee.setShainPassword(sf.getEmployeeData(employee.getShainId()).getShainPassword());
            employeeRepository.save(employee);
        } else {
            if (employee.getShainId().equals("")) {
                String nextSeq = seq.findBySeqTablename("m_shain").getSeqValue();
                employee.setShainId(nextSeq);
                Integer i = Integer.parseInt(nextSeq);
                i++;
                Sequences sequence = seq.findBySeqTablename("m_shain");
                sequence.setSeqValue(i.toString());
                seq.save(sequence);
            }
            if(employee.getPassword().equals("") || employee.getPassword() == null)
                employee.setShainPassword(passwordEncoder.encode("aim123456"));
            else
                employee.setShainPassword(passwordEncoder.encode(employee.getPassword()));
            Date dt = new Date();

            employee.setResume(new ResumeModel());

            employee.setShainRegisterDate(dt);
            employeeRepository.save(employee);

        }
    }
}
