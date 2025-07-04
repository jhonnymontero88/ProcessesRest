package min.gob.ec.tracingservices.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import min.gob.ec.tracingservices.constants.SecurityConstants;
import min.gob.ec.tracingservices.model.common.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (Objects.nonNull(jwt) && jwtUtils.validateJwtToken(jwt)) {
                UserPrincipal userPrincipal = jwtUtils.getUser(jwt);
                userPrincipal.setPassword1(userPrincipal.getUser().getPassword());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if(request.getRequestURL().toString().toLowerCase().contains("userid")){
                    Map<String, String[]> extraParams = new TreeMap<String, String[]>();
                    extraParams.put("userId", new String[] {userPrincipal.getUser().getId().toString()});
                    HttpServletRequest wrappedRequest = new CustomRequestWrapper(request, extraParams);
                    filterChain.doFilter(wrappedRequest, response);
                }else{
                    filterChain.doFilter(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(SecurityConstants.TOKEN_HEADER);
        /*if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }*/
        if (StringUtils.hasText(headerAuth)) {
            return headerAuth;
        }
        return null;
    }
}
