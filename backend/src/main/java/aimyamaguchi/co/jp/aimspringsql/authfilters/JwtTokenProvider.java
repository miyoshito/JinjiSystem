package aimyamaguchi.co.jp.aimspringsql.authfilters;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import aimyamaguchi.co.jp.aimspringsql.security.UserDetailsServiceImpl;
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


@Component
public class JwtTokenProvider {

  @Autowired
  private UserDetailsServiceImpl userDetailsImpl;

  private Algorithm kee = Algorithm.HMAC256("secret");

  public String createToken(String username, boolean isadmin, List<Long> areas, Long level) {
    long validityInMilliseconds = 259200000;
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    Long[] arr = areas.toArray(new Long[0]);

    String token = JWT.create()
            .withSubject(username)
            .withClaim("authFlag", isadmin)
            .withClaim("level", level)
            .withArrayClaim("area", arr)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .sign(kee);
    return "Bearer " + token;

  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsImpl.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean isAdmin(String token) {
    Claim claim = JWT.decode(token).getClaim("authFlag");
    return claim.asBoolean();
  }

  public List<Integer> getAreas(String token) {
    Claim claim = JWT.decode(token).getClaim("area");
    return claim.asList(Integer.class);
  }

  public Long getAuthority(String token) {
    Claim claim = JWT.decode(token).getClaim("level");
    return claim.asLong();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    else return "";
  }

  public String getUsername(String token) {
    return JWT.decode(token).getSubject();
  }

  public boolean validateToken(String token) {
    if (!token.equals("")) {
      JWTVerifier verifier = JWT.require(kee).build();
      try {
        DecodedJWT jwt = verifier.verify(token);
        return true;
      } catch(CustomException e) {
        return false;
      }
    } else return false;
  }
}