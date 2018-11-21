package aimyamaguchi.co.jp.aimspringsql.authfilters;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import aimyamaguchi.co.jp.aimspringsql.security.Roles;
import aimyamaguchi.co.jp.aimspringsql.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {

  Algorithm kee = Algorithm.HMAC256("secret");

  private long validityInMilliseconds = 604800000; // 1h

  @Autowired
  private UserDetailsServiceImpl myUserDetails;


  public String createToken(String username, Roles roles) {
    //Claims claims = Jwts.claims().setSubject(username);
    //claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
    //claims.put("auth", roles);
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

      String token = JWT.create()
      .withSubject(username)
      .withIssuedAt(now)
      .withExpiresAt(validity)
      .sign(kee);  
    return "Bearer "+token;  

    /*String token = Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(kee)
        .compact();
    return "Bearer "+token;
    */
  }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    System.out.println("token resolve token=>" +req.getHeader("Authorization"));
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public String getUsername(String token) {
    //return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    return JWT.decode(token).getSubject();
  }

    /*public String getSubject(HttpServletRequest req){
      String token = resolveToken(req);

      //Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody();
      
      String subject = claims.getSubject();
      System.out.println(subject);
      return subject;
    }*/

    public boolean validateToken(String token) {
      try {
        JWTVerifier verifier = JWT.require(kee).build();

        DecodedJWT jwt = verifier.verify(token);

          System.out.println(jwt);
        //JWT.decode(token);
        return true;
      } catch (JWTDecodeException exception) {
        throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    /*
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    */
  }

}