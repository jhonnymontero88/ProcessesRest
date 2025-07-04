package min.gob.ec.tracingservices.security;

import min.gob.ec.tracingservices.repository.common.UserRepository;
import min.gob.ec.tracingservices.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.util.Base64;
import java.util.List;

@Service
public class ServiceAuthenticationProvider implements AuthenticationProvider {
    private Client client;

    private WebTarget webTarget;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken up = (UsernamePasswordAuthenticationToken) authentication.getPrincipal();
            return new UsernamePasswordAuthenticationToken(up.getPrincipal(), null);
        }

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Decodificación de nombre y contraseña
        String decodename = decodeBase64WithSize(name);
        String decodepwd = decodeBase64WithSize(password);

        name = decodename;
        password = decodepwd;

        // Obtener usuario desde la base de datos
        String[] nameVector = name.split("#");
        List<User> userList = userRepository.findByEmail(nameVector[1]);

        if (userList.isEmpty()) {
            throw new UsernameNotFoundException("No existe un usuario con los datos ingresados");
        } else if (userList.size() > 1) {
            throw new UsernameNotFoundException("Existe más de un usuario con los datos ingresados");
        }

        User user = userList.get(0);

        if (!user.getActive()) {
            throw new UsernameNotFoundException("El usuario está desactivado");
        }

        // Validar la contraseña utilizando BCrypt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Usuario o clave incorrectos");
        }

        // Crear UserPrincipal y retornar el token de autenticación
        UserPrincipal up = new UserPrincipal(user);
        return new UsernamePasswordAuthenticationToken(up, null, up.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // Método privado para decodificar y extraer el tamaño
    private String decodeBase64WithSize(String encodedString) {
        int size = Integer.parseInt(encodedString.substring(0, 1));
        encodedString = encodedString.substring(1);

        String decodedString = new String(Base64.getDecoder().decode(encodedString));
        return decodedString.substring(size, decodedString.length() - size);
    }
}
