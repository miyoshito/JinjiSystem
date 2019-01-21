package aimyamaguchi.co.jp.aimspringsql.security;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


@Configuration
@EnableJpaAuditing
public class SpringSecurityAuditorAware {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}
class SpringSecurityAuditAwareImpl implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Optional.ofNullable(userPrincipal.getUsername());
    }
}
