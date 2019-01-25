package aimyamaguchi.co.jp.aimspringsql.security;

import aimyamaguchi.co.jp.aimspringsql.authfilters.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class TokenMatcher implements RequestMatcher {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean matches(HttpServletRequest request) {
        return (jwtTokenProvider.validateToken(jwtTokenProvider.resolveToken(request)));
    }
}
