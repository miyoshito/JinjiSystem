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

    public ResponseEntity<String> changePassword(String shainid, String oldPassword, String newPassword){
        EmployeeMaster user = employeeRepository.findByShainId(shainid);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches((CharSequence) oldPassword, user.getPassword())) {
            user.setShainPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<String>resetPassword(){
        return null;
    }
}
