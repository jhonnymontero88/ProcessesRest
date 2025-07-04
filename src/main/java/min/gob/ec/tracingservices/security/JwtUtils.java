package min.gob.ec.tracingservices.security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import min.gob.ec.tracingservices.constants.SecurityConstants;
import min.gob.ec.tracingservices.model.common.Role;
import min.gob.ec.tracingservices.model.common.User;
import min.gob.ec.tracingservices.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal user = ((UserPrincipal) authentication.getPrincipal());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String jsonUser = gson.toJson(user.getUser());
        jsonUser = Base64.getEncoder().encodeToString(jsonUser.getBytes());
        Crypt crypt = null;
        try{
            crypt = new Crypt();
            jsonUser = crypt.encrypt(jsonUser);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return "";
        }

        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(getKey(), SignatureAlgorithm.HS512)
                // .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(jsonUser)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .claim("rol", roles)
                .compact();

        return token;
    }

    private Key getKey(){
        return Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
    }

    public UserPrincipal getUser(String token) {
        Jws<Claims> parsedToken = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
        String jsonUser = parsedToken
                .getBody()
                .getSubject();

        Crypt crypt = null;
        try{
            crypt = new Crypt();
            jsonUser = crypt.decrypt(jsonUser);            
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new SignatureException("Falló al desencriptar");
        }
        jsonUser = new String(Base64.getDecoder().decode(jsonUser));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        User user= gson.fromJson(jsonUser, User.class);
        return new UserPrincipal(user);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(authToken);            
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}