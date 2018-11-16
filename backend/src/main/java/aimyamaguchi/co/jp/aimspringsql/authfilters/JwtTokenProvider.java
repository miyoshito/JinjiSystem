package aimyamaguchi.co.jp.aimspringsql.authfilters;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import aimyamaguchi.co.jp.aimspringsql.security.Roles;
import aimyamaguchi.co.jp.aimspringsql.security.UserDetailsServiceImpl;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  
  private String secretKey = "l_wQklvpsSPZYYBnGUoSfz1blDFScEZLrhUb_WRhShk";

  private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private long validityInMilliseconds = 3600000; // 1h

  @Autowired
  private UserDetailsServiceImpl myUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  

  public String createToken(String username, Roles roles) {
    Claims claims = Jwts.claims().setSubject(username);
    //claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
    claims.put("auth", roles);
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    String token = Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(key)
        .compact();
    return "Bearer "+token;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

    public String getSubject(HttpServletRequest req){
      String token = resolveToken(req);
      Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody();
      
      String subject = claims.getSubject();
      System.out.println(subject);
      return subject;
    }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}