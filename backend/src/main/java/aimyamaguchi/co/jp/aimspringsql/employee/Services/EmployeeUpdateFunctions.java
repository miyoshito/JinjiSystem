package aimyamaguchi.co.jp.aimspringsql.employee.Services;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeUpdateFunctions {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean changePassword(String shainid, String oldPassword, String newPassword){
        EmployeeMaster user = employeeRepository.findByShainId(shainid);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches((CharSequence) oldPassword, user.getShainPassword())) {
            user.setShainPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public void resetPassword(String id){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        EmployeeMaster user = employeeRepository.findByShainId(id);
        user.setShainPassword(passwordEncoder.encode("aim123456"));
        employeeRepository.save(user);
    }
}
