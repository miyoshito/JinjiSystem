package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
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
import java.time.LocalDate;
import java.util.Date;

@Service
public class EmployeeInsertFunctions {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SequenceInterface seq;
    @Autowired
    private SearchFilters sf;

    @Autowired
    private JwtTokenProvider jwt;


    public void insertEmployee(EmployeeMaster employee, HttpServletRequest req){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String token = req.getHeader("authorization");

        if (!employee.getShainId().equals("") && employeeRepository.findByShainId(employee.getShainId()) != null){
            employee.setResume(sf.getEmployeeWithResume(employee.getShainId()).getResume());
            employee.setAdmin(employee.isAdmin());
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
            if(employee.getShainPassword().equals("") || employee.getShainPassword() == null)
                employee.setShainPassword(passwordEncoder.encode("aim123456"));
            else
                employee.setShainPassword(passwordEncoder.encode(employee.getShainPassword()));
            Date dt = new Date();
            employee.setResume(new ResumeModel());
            employeeRepository.save(employee);

        }
    }
}
