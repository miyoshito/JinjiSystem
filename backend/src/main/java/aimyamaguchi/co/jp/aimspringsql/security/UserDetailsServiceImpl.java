package aimyamaguchi.co.jp.aimspringsql.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeRepository;
import aimyamaguchi.co.jp.aimspringsql.employee.EmployeeService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository er;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeMaster employee = er.findByShainId(username);
        Set<Roles> authorities = new HashSet<Roles>();
        
        if (employee.getShainId() == "") throw new UsernameNotFoundException("Could not find the user "+username);       

        authorities.add(employee.getRole());


        return new User(employee.getUsername(), employee.getPassword(), authorities);
	}

}