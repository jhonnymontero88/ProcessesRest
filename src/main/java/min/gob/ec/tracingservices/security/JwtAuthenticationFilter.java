package min.gob.ec.tracingservices.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.GsonBuilder;
import min.gob.ec.tracingservices.constants.SecurityConstants;
import min.gob.ec.tracingservices.model.common.Role;
import min.gob.ec.tracingservices.model.common.User;
import min.gob.ec.tracingservices.util.Crypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.addHeader(SecurityConstants.ERROR_HEADER, failed.getMessage());
        response.addHeader("Access-Control-Expose-Headers", SecurityConstants.ERROR_HEADER);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        UserPrincipal user = ((UserPrincipal) authentication.getPrincipal());
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte [] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        /*Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String jsonUser = gson.toJson(user.getUser());*/
        String jsonUser = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        try{
            jsonUser = objectMapper.writeValueAsString(user.getUser());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return;
        }
        jsonUser = Base64.getEncoder().encodeToString(jsonUser.getBytes());
        Crypt crypt = null;
        try{
            crypt = new Crypt();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return;
        }
        try{
            jsonUser = crypt.encrypt(jsonUser);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        User user1 = new User();
        user1.setName(user.getUser().getName());
        user1.setInstitution(user.getUser().getInstitution());
        user1.setEmail(user.getUser().getEmail());
        user1.setRole(new Role());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String jsonUser1 = gson.toJson(user1);
        jsonUser1 = Base64.getEncoder().encodeToString(jsonUser1.getBytes());

        String token = Jwts.builder()
                // .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                // .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(jsonUser)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                // .claim("rol", roles)
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader(SecurityConstants.USER_HEADER, jsonUser1);
        response.addHeader("Access-Control-Expose-Headers", SecurityConstants.USER_HEADER);
        response.addHeader("Access-Control-Expose-Headers", SecurityConstants.TOKEN_HEADER);
    }
}
