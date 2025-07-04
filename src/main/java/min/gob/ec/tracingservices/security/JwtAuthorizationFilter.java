package min.gob.ec.tracingservices.security;

import com.google.gson.GsonBuilder;
import min.gob.ec.tracingservices.constants.SecurityConstants;
import min.gob.ec.tracingservices.model.common.User;
import min.gob.ec.tracingservices.util.Crypt;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private UserPrincipal userPrincipalTmp;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        String header = request.getHeader(SecurityConstants.TOKEN_HEADER);
        String method = request.getMethod();

        if(HttpMethod.DELETE.matches(method) || HttpMethod.PUT.matches(method)){
            response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
            filterChain.doFilter(request, response);
            return;
        }

        if (header == null || header.isEmpty() || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(request.getRequestURL().toString().toLowerCase().contains("userid")){
            Map<String, String[]> extraParams = new TreeMap<String, String[]>();
            extraParams.put("userId",
                    new String[] {this.userPrincipalTmp.getUser().getId().toString()});
            HttpServletRequest wrappedRequest = new CustomRequestWrapper(request, extraParams);
            filterChain.doFilter(wrappedRequest, response);
        }else{
            filterChain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if ( token != null && !token.isEmpty() ) {
            try {
                byte [] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                String jsonUser = parsedToken
                        .getBody()
                        .getSubject();

                Crypt crypt = null;
                try{
                    crypt = new Crypt();
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    throw new SignatureException("Falló al desencriptar");
                }
                try{
                    jsonUser = crypt.decrypt(jsonUser);
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    throw new SignatureException("Falló al desencriptar");
                }
                jsonUser = new String(Base64.getDecoder().decode(jsonUser));

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                User user= gson.fromJson(jsonUser, User.class);
                UserPrincipal userPrincipal = new UserPrincipal(user);
                this.userPrincipalTmp = userPrincipal;

                Authentication au = new UsernamePasswordAuthenticationToken(userPrincipal, null);

                if ( userPrincipal.getUsername() != null && !userPrincipal.getUsername().isEmpty()) {
                    return new UsernamePasswordAuthenticationToken(au, null);
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }

        return null;
    }


}

