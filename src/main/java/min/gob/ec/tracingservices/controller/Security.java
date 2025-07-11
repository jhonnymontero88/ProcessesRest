package min.gob.ec.tracingservices.controller;

import java.lang.System;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import min.gob.ec.tracingservices.model.common.*;
import min.gob.ec.tracingservices.security.JwtUtils;
import min.gob.ec.tracingservices.security.ServiceAuthenticationProvider;
import min.gob.ec.tracingservices.security.UserPrincipal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import min.gob.ec.tracingservices.repository.common.RoleRepository;
import min.gob.ec.tracingservices.repository.common.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("security")
public class Security {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceAuthenticationProvider serviceAuthenticationProvider;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String saveUser(@RequestBody User user) throws Exception {
        User _user = new User();
        if (user.getId() != null && user.getId() > 0) {
            _user = userRepository.findById(user.getId()).orElse(new User());
            if (_user.getId() == null || _user.getId() == 0) {
                throw new Exception("No se pudo obtener los datos del usuario.");
            }
        }

        boolean isNew = false;
        String pwd = getRandomPwd();
        if (_user.getId() != null && _user.getId() > 0) {
            _user.setName(user.getName());
            _user.setPerson(user.getPerson());
            _user.setInstitution(user.getInstitution());
            _user.setRole(user.getRole());
            _user.setEmail(user.getEmail());
            _user.setActive(user.getActive());
            userRepository.save(_user);
        } else {
            isNew = true;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte[] digest = md.digest();
            String passHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

            _user = new User();
            _user.setName(user.getName());
            _user.setPerson(user.getPerson());
            _user.setInstitution(user.getInstitution());
            _user.setRole(user.getRole());
            _user.setEmail(user.getEmail());
            _user.setActive(true);
            _user.setPassword(passHash);
            userRepository.save(_user);
        }

        JSONObject jo = new JSONObject();
        jo.appendField("status", "200");
        jo.appendField("message", "Proceso realizado con éxito.");
        return jo.toString();
    }

    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String changePwd(@RequestParam("current") String current, @RequestParam("pwd") String pwd) throws Exception {
        current = new String(Base64.getDecoder().decode(current));
        pwd = new String(Base64.getDecoder().decode(pwd));

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal == null || userPrincipal.getUser() == null || userPrincipal.getUser().getId() == 0) {
            throw new Exception("No se pudo obtener los datos del usuario.");
        }

        User user = userRepository.findById(userPrincipal.getUser().getId())
                .orElseThrow(() -> new Exception("No se pudo obtener los datos del usuario."));

        if (!passwordEncoder.matches(current, user.getPassword())) {
            throw new Exception("La clave actual no es la correcta.");
        }

        String newHashedPassword = passwordEncoder.encode(pwd);
        user.setPassword(newHashedPassword);
        userRepository.save(user);

        JSONObject jo = new JSONObject();
        jo.put("status", "200");
        jo.put("message", "Proceso realizado con éxito");
        return jo.toString();
    }

    @RequestMapping(value = "/changepwd1", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String changePwd1(@RequestParam("id") Integer id) throws Exception {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getId() == null || user.getId() == 0) {
            throw new Exception("No se pudo obtener los datos del usuario.");
        }

        String pwd = getRandomPwd();
        String passHash = passwordEncoder.encode(pwd);
        user.setPassword(passHash);
        userRepository.save(user);

        JSONObject jo = new JSONObject();
        jo.put("status", "200");
        jo.put("message", "Proceso realizado con éxito. Clave: " + pwd);
        return jo.toString();
    }

    private String getRandomPwd() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();

        return random
                .ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = serviceAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            UserPrincipal user = ((UserPrincipal) authentication.getPrincipal());
            User user1 = new User();
            user1.setName(user.getUser().getName());
            user1.setInstitution(user.getUser().getInstitution());
            user1.setEmail(user.getUser().getEmail());
            user1.setRole(new Role());
            String jsonUser = gson.toJson(user1);
            jsonUser = Base64.getEncoder().encodeToString(jsonUser.getBytes());

            return ResponseEntity.ok(LoginVerificationResponse.builder()
                    .user(jsonUser)
                    .tokenValid(Boolean.FALSE)
                    .authValid(Boolean.TRUE)
                    .message("Autenticación realizada.")
                    .jwt(this.jwtUtils.generateJwtToken(authentication))
                    .error(Boolean.FALSE)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(LoginVerificationResponse.builder()
                    .user("")
                    .tokenValid(Boolean.FALSE)
                    .authValid(Boolean.FALSE)
                    .message(e.getMessage())
                    .jwt("")
                    .error(Boolean.TRUE)
                    .build());
        }
    }
}
