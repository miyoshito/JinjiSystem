package aimyamaguchi.co.jp.aimspringsql.authfilters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aimyamaguchi.co.jp.aimspringsql.security.CustomAuthenticationProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class CustomAuthenticatorFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    private CustomAuthenticationProvider customAuthenticationProvider;

    public CustomAuthenticatorFilter(JwtTokenProvider jwtTokenProvider) {
      this.jwtTokenProvider = jwtTokenProvider;
    }
  
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {
  
      String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
      try {
        if (token != null && jwtTokenProvider.validateToken(token)) {
          Authentication auth = jwtTokenProvider.getAuthentication(token);
          SecurityContextHolder.getContext().setAuthentication(auth);
        }

      } catch (CustomException ex) {
        HttpServletResponse response = (HttpServletResponse) res;
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      filterChain.doFilter(req, res);
    }
  
}