package aimyamaguchi.co.jp.aimspringsql.authfilters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class CustomAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public CustomAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
      this.jwtTokenProvider = jwtTokenProvider;
    }
  
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {


      HttpServletRequest httpReq = (HttpServletRequest) req;
      HttpServletResponse httpRes = (HttpServletResponse) res; 
  
      String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

      Enumeration<String> headersname = httpReq.getHeaderNames();
      httpRes.addHeader("a", "");


      while (headersname.hasMoreElements()){
        System.out.println(headersname.nextElement());
      }
      System.out.println("token getting auth =>" +httpReq.getHeader("Authorization"));  
      System.out.println("token getting acrh =>" +httpReq.getHeader("access-control-request-headers"));  
      System.out.println("token recebido =>" +token);
      try {
        if (token != null && jwtTokenProvider.validateToken(token)) {
          System.out.println("Token valido !");
          Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (CustomException ex) {
        HttpServletResponse response = (HttpServletResponse) res;
        System.out.println("token Invalido" );
        response.sendError(ex.getHttpStatus().value(), ex.getMessage());
        return;
      }
  
      filterChain.doFilter(req, res);
    }
  
}