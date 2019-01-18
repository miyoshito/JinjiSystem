package aimyamaguchi.co.jp.aimspringsql.security;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenFilterConfigurer;
import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;

import static com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Bean 
    @Override 
    public AuthenticationManager authenticationManagerBean() throws Exception { 
        return super.authenticationManagerBean(); 
    }

    @Bean
    public Module module() {
        Hibernate5Module h5m = new Hibernate5Module();
        h5m.disable(USE_TRANSIENT_ANNOTATION);
        return h5m;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .csrf().disable()
                .cors().and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring()
      .antMatchers("GET", "/api/public/**")
      .antMatchers("/assets/**");
    }



}