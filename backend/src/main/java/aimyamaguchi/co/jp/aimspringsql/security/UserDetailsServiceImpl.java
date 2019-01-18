package aimyamaguchi.co.jp.aimspringsql.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import aimyamaguchi.co.jp.aimspringsql.employee.Models.EmployeeMaster;
import aimyamaguchi.co.jp.aimspringsql.employee.Repositories.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository er;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeMaster employee = er.findByShainId(username);

        if (employee.getShainId().equals("")) throw new UsernameNotFoundException("Could not find the user "+username);
        return new User(employee.getShainId(), employee.getShainPassword(), new HashSet<>());
    }

}