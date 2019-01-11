package aimyamaguchi.co.jp.aimspringsql.authfilters;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import aimyamaguchi.co.jp.aimspringsql.security.Roles;
import aimyamaguchi.co.jp.aimspringsql.security.UserDetailsServiceImpl;


@Component
public class JwtTokenProvider {

  private Algorithm kee = Algorithm.HMAC256("secret");



  @Autowired
  private UserDetailsServiceImpl myUserDetails;


  public String createToken(String username, Roles roles, List<Long> areas, Long level) {
  long validityInMilliseconds = 259200000;
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    Long[] arr = areas.stream().toArray(Long[]::new);

    String token = JWT.create()
      .withSubject(username)
      .withClaim("role", roles.getRoledesc())
      .withClaim("level", level)
      .withArrayClaim("area", arr)
      .withIssuedAt(now)
      .withExpiresAt(validity)
      .sign(kee);  
    return "Bearer "+token;

  }

  public String getRole(String token){
    Claim claim = JWT.decode(token).getClaim("role");
    return claim.asString();
  }
  public Optional<String> getRolee(String token){
    Claim claim = JWT.decode(token).getClaim("role");
    return Optional.of(claim.asString());
  }

  public List<Long> getAreas(String token){
      Claim claim = JWT.decode(token).getClaim("area");
      return claim.asList(Long.class);
  }

  public Long getAuthority(String token){
    Claim claim = JWT.decode(token).getClaim("level");
    return claim.asLong();
  }

  public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return "";
  }

  public String getUsername(String token) {
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
        //JWT.decode(token);
        return true;
      } catch (JWTDecodeException exception) {
        return false;
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