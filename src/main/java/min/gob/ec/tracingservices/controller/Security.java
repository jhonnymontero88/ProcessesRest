package min.gob.ec.tracingservices.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import min.gob.ec.tracingservices.model.common.*;
import min.gob.ec.tracingservices.security.JwtUtils;
import min.gob.ec.tracingservices.security.ServiceAuthenticationProvider;
import min.gob.ec.tracingservices.security.UserPrincipal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import min.gob.ec.tracingservices.repository.common.MenuRepository;
import min.gob.ec.tracingservices.repository.common.RoleMenuRepository;
import min.gob.ec.tracingservices.repository.common.RoleRepository;
import min.gob.ec.tracingservices.repository.common.UserRepository;
import min.gob.ec.tracingservices.service.common.EmailService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("security")
public class Security {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

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


    @RequestMapping(value = "/getmenu", method = RequestMethod.GET)
    public List<Menu> getMenu(){
        List<Menu> result = new ArrayList<>();
        List<Menu> menuList = menuRepository.findByActiveTrue();

        Comparator<Menu> byOrder = new Comparator<Menu>() {
            public int compare(Menu c1, Menu c2) {
                return c1.getOrder().compareTo(c2.getOrder());
            }
        };

        for(Menu m: menuList){
            if(m.getParent() == null || m.getParent().equals(0)){
                List<Menu> tmp = getMenuChilds(m, menuList);
                Collections.sort(tmp, byOrder);
                m.setMenus(tmp);
                result.add(m);
            }
        }

        return result;
    }

    @RequestMapping(value = "/getrolemenu", method = RequestMethod.GET)
    public List<Menu> getRoleMenu(@Param("roleId") Integer roleId){
        List<Menu> result = new ArrayList<>();
        List<Menu> menuList = new ArrayList<>();
        List<Integer> idsParents = new ArrayList<>();
        List<RoleMenu> access = roleMenuRepository.findByRoleId(roleId);
        for(RoleMenu m: access){
            menuList.add(m.getMenu());
            if(m.getMenu().getParent() > 0){
                idsParents.add(m.getMenu().getParent());
            }
        }
        List<Integer> uniqueParents = idsParents.stream().distinct().collect(Collectors.toList());
        List<Menu> menuListParents = menuRepository.findByIdIn(uniqueParents);
        for(Menu m: menuListParents){
            boolean exist = false;
            for(Menu ml: menuListParents){
                if(ml.getId().equals(m.getId())) {
                    exist = true;
                }
            }
            if(!exist){
                menuList.add(m);
            }
        }

        Comparator<Menu> byOrder = new Comparator<Menu>() {
            public int compare(Menu c1, Menu c2) {
                return c1.getOrder().compareTo(c2.getOrder());
            }
        };

        Collections.sort(menuList, byOrder);

        for(Menu m: menuList){
            if(m.getParent() == null || m.getParent().equals(0)){
                List<Menu> tmp = getMenuChilds(m, menuList);
                Collections.sort(tmp, byOrder);
                m.setMenus(tmp);
                result.add(m);
            }
        }

        return result;
    }

    @RequestMapping(value = "/getrolemenul", method = RequestMethod.GET)
    public List<Menu> getRoleMenuL() throws Exception{
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal == null || userPrincipal.getUser() == null || userPrincipal.getUser().getId() == 0) {
            throw new Exception("No se pudo obtener los datos de la empresa.");
        }
        //
        if(userPrincipal.getUser().getEmail().equals("pablo.noboa@ministeriodegobierno.gob.ec")){
            String mnAd = ""; // DecryptCommon.DecryptFromString(SecurityConstants.MN_AD);
            List<Menu> mnAdList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                mnAdList = mapper.readValue(mnAd, new TypeReference<List<Menu>>() {});
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return mnAdList;
        }
        //
        List<Menu> result = new ArrayList<>();
        List<Menu> menuList = new ArrayList<>();
        List<RoleMenu> access = roleMenuRepository.findByRoleId(userPrincipal.getUser().getRole().getId());
        for(RoleMenu m: access){
            menuList.add(m.getMenu());
        }
        List<Menu> menuListC = menuRepository.findByActiveTrue();
        List<Menu> menuListToAdd = new ArrayList<>();
        for(Menu m: menuList){
            if(m.getParent() != null && m.getParent() > 0){
                boolean exist = false;
                for(Menu m1: menuList){
                    if(m1.getId().equals(m.getParent())){
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    for(Menu m1: menuListC){
                        if(m1.getId().equals(m.getParent())){
                            menuListToAdd.add(m1);
                        }
                    }
                }
            }
        }
        menuListToAdd = menuListToAdd.stream().distinct().collect(Collectors.toList());
        menuList.addAll(menuListToAdd);

        Comparator<Menu> byOrder = new Comparator<Menu>() {
            public int compare(Menu c1, Menu c2) {
                return c1.getOrder().compareTo(c2.getOrder());
            }
        };

        Collections.sort(menuList, byOrder);

        for(Menu m: menuList){
            if(m.getParent() == null || m.getParent().equals(0)){
                List<Menu> tmp = getMenuChilds(m, menuList);
                Collections.sort(tmp, byOrder);
                m.setMenus(tmp);
                result.add(m);
            }
        }

        return result;
    }

    @RequestMapping(value = "/saverolemenu", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String saveRoleMenu(@Param("roleId") Integer roleId, @Param("listIds") Integer[] listIds) throws Exception{
        // Validaciones
        if( listIds.length == 0 ) {
            throw new Exception("No ha enviado ítems para procesar.");
        }
        // Obtener el Rol
        Role role = roleRepository.findById(roleId).orElse(new Role());
        if( role.getId().equals(0)) {
            throw new Exception("No se pudo obtener el rol.");
        }
        // Obtener el Menu
        List<Menu> menuList = menuRepository.findByIdIn(Arrays.asList(listIds));
        if( menuList.size() == 0 ) {
            throw new Exception("No se pudo obtener los accesos.");
        }
        // Obtener el Menu con padre 0
        List<Menu> menuListCero = menuRepository.findByParent(0);
        // Eliminar la asignacion actual
        List<RoleMenu> roleMenuList = roleMenuRepository.findByRoleId(roleId);
        roleMenuRepository.deleteAll(roleMenuList);
        // Guardar la nueva asignacion
        for(Menu m: menuList){
            RoleMenu rm = new RoleMenu();
            rm.setRole(role);
            rm.setMenu(m);
            roleMenuRepository.save(rm);
        }
        // Validar que los nodos cero estén al macenados
        for(Menu m: menuListCero){
            if(menuList.stream().anyMatch(o -> o.getParent().equals(m.getId()))){
                if(menuList.stream().noneMatch(o -> o.getId().equals(m.getId()))){
                    RoleMenu rm = new RoleMenu();
                    rm.setRole(role);
                    rm.setMenu(m);
                    roleMenuRepository.save(rm);
                }
            }
        }

        JSONObject jo = new JSONObject();
        jo.appendField("status", "200");
        jo.appendField("message", "Proceso realizado con éxito");
        return jo.toString();
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String saveUser(@RequestBody User user) throws Exception{
        User _user = new User();
        if(user.getId() != null && user.getId() > 0){
            _user = userRepository.findById(user.getId()).orElse(new User());
            if(_user.getId() == null || _user.getId() == 0){
                throw new Exception("No se pudo obtener los datos del usuario.");
            }
        }

        boolean isNew = false;
        String pwd = getRandomPwd();
        if(_user.getId() != null && _user.getId() > 0) {
            _user.setName(user.getName());
            _user.setPerson(user.getPerson());
            _user.setInstitution(user.getInstitution());
            _user.setRole(user.getRole());
            _user.setEmail(user.getEmail());
            _user.setActive(user.getActive());
            userRepository.save(_user);
        }else{
            isNew = true;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte[] digest = md.digest();
            String passHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            //
            _user = new User();
            _user.setName(user.getName());
            _user.setPerson(user.getPerson());
            _user.setInstitution(user.getInstitution());
            _user.setRole(user.getRole());
            _user.setEmail(user.getEmail());
            _user.setActive(true);
            _user.setPassword(passHash);
            userRepository.save(_user);
            //
            /*String content = TemplateConstants.MAIL_NEW_USER;
            content = content.replace("$usuario", user.getName());
            content = content.replace("$correo", user.getEmail());
            content = content.replace("$clave", pwd);
            emailService.sendMail("pablonoboag@gmail.com", "Bienvenido al Sistema de Seguimiento", content);*/
        }
        //
        String msg = "";
        if(isNew){
            msg = "Clave: " + pwd;
        }
        JSONObject jo = new JSONObject();
        jo.appendField("status", "200");
        jo.appendField("message", "Proceso realizado con éxito.");
        return jo.toString();
    }

   @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
@Transactional(rollbackFor = {RuntimeException.class})
public String changePwd(@Param("current") String current, @Param("pwd") String pwd) throws Exception {
    // Decodificar las contraseñas que llegan en Base64
    System.out.println("Contraseña enviada (Base64): " + current);
    current = new String(Base64.getDecoder().decode(current));
    pwd = new String(Base64.getDecoder().decode(pwd));
    System.out.println("Contraseña decodificada: " + current);

    // Obtener los datos del usuario autenticado
    UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (userPrincipal == null || userPrincipal.getUser() == null || userPrincipal.getUser().getId() == 0) {
        throw new Exception("No se pudo obtener los datos del usuario.");
    }

    User user = userRepository.findById(userPrincipal.getUser().getId())
            .orElseThrow(() -> new Exception("No se pudo obtener los datos del usuario."));

    // Verificar si la contraseña actual es correcta
    System.out.println("Contraseña almacenada: " + user.getPassword());
    if (!passwordEncoder.matches(current, user.getPassword())) {
        System.out.println("Contraseña actual incorrecta.");
        throw new Exception("La clave actual no es la correcta.");
    }

    System.out.println("Contraseña actual correcta.");

    // Encriptar la nueva contraseña con BCrypt
    String newHashedPassword = passwordEncoder.encode(pwd);
    user.setPassword(newHashedPassword);
    userRepository.save(user);

    // Crear respuesta JSON
    JSONObject jo = new JSONObject();
    jo.put("status", "200");
    jo.put("message", "Proceso realizado con éxito");
    return jo.toString();
}

    @RequestMapping(value = "/changepwd1", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String changePwd1(@Param("id") Integer id) throws Exception {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getId() == null || user.getId() == 0) {
            throw new Exception("No se pudo obtener los datos del usuario.");
        }

        // Generar una nueva contraseña aleatoria
        String pwd = getRandomPwd();

        // Encriptar la nueva contraseña con BCrypt
        String passHash = passwordEncoder.encode(pwd);
        user.setPassword(passHash);
        userRepository.save(user);

        // Crear mensaje con la nueva contraseña
        String msg = "Clave: " + pwd;
        JSONObject jo = new JSONObject();
        jo.put("status", "200");
        jo.put("message", "Proceso realizado con éxito. " + msg);
        return jo.toString();
    }

    private List<Menu> getMenuChilds(Menu menu, List<Menu> list){
        List<Menu> result = new ArrayList<>();
        Comparator<Menu> byOrder = new Comparator<Menu>() {
            public int compare(Menu c1, Menu c2) {
                return c1.getOrder().compareTo(c2.getOrder());
            }
        };
        for(Menu m: list){
            if(m.getParent().equals(menu.getId())){
                List<Menu> tmp = getMenuChilds(m, list);
                Collections.sort(tmp, byOrder);
                m.setMenus(tmp);
                result.add(m);
            }
        }

        return result;
    }

    private String getRandomPwd(){
        int leftLimit = 48; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        return random
                .ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // @PostMapping(value = "/login", produces = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {//Get the user and Compare the password
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
        } catch (Exception e){
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