package min.gob.ec.tracingservices.controller;

import min.gob.ec.tracingservices.model.common.*;
import min.gob.ec.tracingservices.model.suiosr.*;
import min.gob.ec.tracingservices.repository.common.*;
import min.gob.ec.tracingservices.repository.suiosr.*;
import min.gob.ec.tracingservices.security.UserPrincipal;
import min.gob.ec.tracingservices.service.common.OrganizationService;
import min.gob.ec.tracingservices.util.OrganizationSpecification;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import min.gob.ec.tracingservices.repository.suiosr.OrganizationRepository;
import min.gob.ec.tracingservices.model.suiosr.Organization;






import static min.gob.ec.tracingservices.util.UtilSuiosr.createResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("suiosr")
public class SuiosrController {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ExpedientRepository expedientRepository;
    @Autowired
    private TracingRepository tracingRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private OrganizationService organizationService;


    String status = "";
    String message = "";
    @Autowired
    private ReformRepository reformRepository;
    @Autowired
    private LocationRepository locationRepository;


    //CONTROLADOR PARA CONSULTAR POR CI SI EXISTE UNA PERSONA
    @GetMapping("ci/{documentNumber}")
    public String findByCi(@PathVariable String documentNumber) {
        status = "200";
        try {
            Person person = personRepository.findByCi(documentNumber);
            if (person == null) {
                throw new Exception("Persona no Encontrada");
            }
            message = "Persona encontrada";
            JSONObject jo = createResponse(status, message);
            jo.appendField("person", person);
            return jo.toString();
        } catch (Exception e) {
            status = "404";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details",e.toString());
            return jo.toString();
        }
    }

    //CONTROLADOR PARA CONSULTAR POR PASAPORTE SI EXISTE UNA PERSONA
    @GetMapping("passport/{documentNumber}/{nationality}")
    public String findByPassport(@PathVariable String documentNumber, @PathVariable String nationality) {
        status = "200";
        try {
            Person person = personRepository.findByPassport(documentNumber, nationality);
            if (person == null) {
                throw new Exception("Persona no Encontrada");
            }
            message = "Persona encontrada";
            JSONObject jo = createResponse(status, message);
            jo.appendField("person", person);
            return jo.toString();
        } catch (Exception e) {
            status = "404";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details",e.toString());
            return jo.toString();
        }
    }

    //CONTROLADOR PARA CREAR NUEVA ORGANIZACIÓN (PrimerTAB)
    @RequestMapping(value = "/saveorganization", method = RequestMethod.POST)
    public String saveOrganization (@RequestBody Organization organization){
        Organization organizationBD;
        try {
            organizationBD = organizationRepository.findFirstByName(organization.getName());
            if (organizationBD != null && !organizationBD.getId().equals(organization.getId())) {
                throw new Exception("Ya existe una organización con el mismo nombre.");
            }
            organizationBD = new Organization();
            boolean isNew = true;
            if(organization.getId() != null && organization.getId() > 0){
                organizationBD = organizationRepository.findById(organization.getId()).orElse(new Organization());
                isNew = false;
            }
            //
            organizationBD.setInstitution(organization.getInstitution());
            organizationBD.setRuc(organization.getRuc());
            organizationBD.setName(organization.getName());
            organizationBD.setEmail(organization.getEmail());
            organizationBD.setPhone(organization.getPhone());
            organizationBD.setTypeOrganization(organization.getTypeOrganization());
            organizationBD.setCurrentReligious(organization.getCurrentReligious());
            organizationBD.setStatusOrganization(organization.getStatusOrganization());
            organizationBD.setInternalState(organization.getInternalState());
            if(isNew){
                organizationBD.setRegistrationCompleted(false);
            }
            //
            organizationRepository.save(organizationBD);
            if(isNew){
                Filial filialMatrix = new Filial();
                filialMatrix.setOrganization(organizationBD);
                filialMatrix.setMatrix(true);
                filialMatrix.setName(organizationBD.getName());
                filialRepository.save(filialMatrix);
            }
            message="Organización guardada con éxito";
            JSONObject jo = createResponse("200", message);
            jo.appendField("obj", organizationBD);
            return jo.toString();
        } catch (Exception e) {
            message = e.getMessage();
            JSONObject jo = createResponse("400", message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    //CONTROLADOR PARA CREAR REPRESENTANTE LEGAL
    @RequestMapping(value = "/legalrepresentative", method = RequestMethod.POST)
    public String saveContact (@RequestBody Member dataMember){
        status = "200";
        try {
            Filial filialSelected = filialRepository.findFirstByOrganizationIdAndMatrixTrue(dataMember.getOrganization().getId());
            Charge chargeSelected = chargeRepository.findChargeByCode("representante_legal");
            // Relación miembro Representante Legal
            Member newMember = new Member();
            newMember.setId(dataMember.getId());
            newMember.setPerson(dataMember.getPerson());
            newMember.setCharge(chargeSelected);
            newMember.setFilial(filialSelected);
            newMember.setStatus(true);
            //
            if(!filialSelected.getOrganization().getRegistrationCompleted()){
                filialSelected.getOrganization().setRegistrationCompleted(true);
                organizationRepository.save(filialSelected.getOrganization());
            }
            //
            memberRepository.save(newMember);
            message = "Representante Legal guardado con éxito.";
            JSONObject jo = createResponse(status, message);
            jo.appendField("obj", newMember);
            return jo.toString();
        } catch (Exception e) {
            status="400";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    //CONTROLADOR PARA CREAR NUEVO EXPEDIENTE
    @RequestMapping(value = "/saveexpedient", method = RequestMethod.POST)
    public String saveExpedient (@RequestBody Expedient expedient){
        status="200";
        try {
           expedientRepository.save(expedient);
            message = "Expediente guardado con éxito.";
            JSONObject jo = createResponse(status, message);
            jo.appendField("id_organization", expedient.getOrganization().getId());
            return jo.toString();
        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    //CONTROLADOR PARA CREAR PERSONA
    @RequestMapping(value = "/saveperson", method = RequestMethod.POST)
    public String savePerson (@RequestBody Person person){
        status="200";
        try {

            Person personSelected = personRepository.findByCi(person.getDocumentNumber());
            if(personSelected != null){
                throw new Exception("Ya existe persona con ese número de Documento.");
            }
            else {
                personRepository.save(person);
                message="Persona guardada con éxito.";
                JSONObject jo = createResponse(status,message);
                jo.appendField("person", person);
                return jo.toString();
            }
        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }
 

    // CONTROLADOR PARA CREAR MIEMBRO
    @RequestMapping(value = "/savemember", method = RequestMethod.POST)
    public String saveMember(@RequestBody Map<String, Object> dataMember) {
        status="200";
        try {

            Map<String, Object> personMap = (Map<String, Object>) dataMember.get("person");
            Map<String, Object> memberMap = (Map<String, Object>) dataMember.get("member");
            Map<String, Object> filialMap = (Map<String, Object>) memberMap.get("filial");
            Map<String, Object> chargeMap = (Map<String, Object>) memberMap.get("charge");
            // Verificar si la persona existe
            Person existingPerson = personRepository.findById((Integer) personMap.get("id")).orElse(null);
            if (existingPerson == null) {
                throw new Exception("Persona no encontrada.");
            }
            // Verificar si la persona tiene al menos un contacto registrado
            List<Contact> contacts = contactRepository.findByPersonId(existingPerson.getId());
            if (contacts.isEmpty()) {
                throw new Exception("Debe registrar al menos un contacto antes de crear un miembro.");
            }
            // Seleccionar Filial y Cargo para Miembro
            Filial filialSelected = filialRepository.findByName((String) filialMap.get("name"));
            Charge chargeSelected = chargeRepository.findByName((String) chargeMap.get("name"));
            // Verificar si ya existe un miembro con ese cargo y que sea directivo en la misma filial
            // se comenta la verificacion, 11/11/2024 Jessica pide que se pueda registrar mas cargos directivos en una misma filial
            //List<Member> existingMembers = memberRepository.findByFilialAndChargeAndDirectiveTrue(filialSelected, chargeSelected);
            // Si existe un miembro con ese cargo y es directivo, verificar el estado
            //for (Member existingMember : existingMembers) {
            //    if (existingMember.isStatus()) { // Si el miembro está activo
            //        throw new Exception("Ya existe un miembro con cargo directivo en esta filial. No se puede agregar otro miembro con el mismo cargo.");
            //    }
            //}
            // Crear y guardar el nuevo miembro
            Member newMember = new Member();
            newMember.setPerson(existingPerson);
            newMember.setCharge(chargeSelected);
            newMember.setFilial(filialSelected);
            newMember.setStatus((Boolean) memberMap.get("status"));
            String dateInitialString = (String) memberMap.get("dateInitial");
            if(dateInitialString == null){
                newMember.setDateInitial(null);
            }else {
                Date dateInitial = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateInitialString);
                newMember.setDateInitial(dateInitial);
            }
            String dateFinalString = (String) memberMap.get("dateEnd");
            if(dateFinalString == null){
                newMember.setDateEnd(null);
            }else{
                Date dateFinal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateFinalString);
                newMember.setDateEnd(dateFinal);
            }

            memberRepository.save(newMember);
            message="Miembro guardado con éxito.";
            JSONObject jo = createResponse(status,message);
            return jo.toString();
        } catch (Exception e) {
            status = "400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            return jo.toString();
        }
    }

    @GetMapping("findExpedient/{idOrganization}")
    public String findExpedienteByOrganization(@PathVariable Integer idOrganization) {
        status = "200";
        try {
            Expedient expedient = expedientRepository.findFirstByOrganizationId(idOrganization);
            if (expedient == null) {
                throw new Exception("Expediente no encontrado");
            }
            message="Expediente encontrado";
            JSONObject jo = createResponse(status,message);
            jo.appendField("expedient", expedient);
            return jo.toString();
        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    @GetMapping("findTracing/{idOrganizacion}")
    public String getTracing(@PathVariable Integer idOrganizacion) {
        status = "200";
        try {
            List<Tracing> tracings = tracingRepository.fAllOByOrganization(idOrganizacion);
            if (tracings == null) {
                throw new Exception("Error al encontrar Acciones");
            }
            message = "Acciones encontradas";
            JSONObject jo = createResponse(status, message);
            JSONArray tracingsArray = new JSONArray();
            for (Tracing tracing : tracings) {
                JSONObject tracingObject = new JSONObject();
                tracingObject.appendField("creationUser", tracing.getCreationUser());
                tracingObject.appendField("observation", tracing.getObservation());
                tracingObject.appendField("dateAnswer", tracing.getDateAnswer() != null
                    ? tracing.getDateAnswer().toString()
                    : ""); // Verificación de nulo
                tracingObject.appendField("organization", tracing.getOrganization().getName());
                tracingObject.appendField("creationDate", tracing.getCreationDate() != null 
                    ? tracing.getCreationDate().toString() 
                    : ""); // Verificación de nulo
                tracingObject.appendField("actionsDoneMDG", tracing.getActionsDoneMDG());
                tracingObject.appendField("registered", tracing.isRegistered());
                tracingObject.appendField("documentIn", tracing.getDocumentIn());
                tracingObject.appendField("documentOut", tracing.getDocumentOut());
                tracingObject.appendField("dateRequest", tracing.getDateRequest() != null
                    ? tracing.getDateRequest().toString()
                    : ""); // Verificación de nulo
                tracingObject.appendField("typeprocedure", tracing.getTypeprocedure());
                tracingObject.appendField("id", tracing.getId());
                
                tracingsArray.add(tracingObject);
            }
            jo.appendField("tracings", tracingsArray);
            return jo.toString();
        } catch (Exception e) {
            status = "500";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }
    


    @RequestMapping(value = "/removefilefromorganization", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class})
    public String removeFileFromProgress(@Param("id") Integer id) {
        String message = "Proceso realizado con éxito.";
        String state = "200";

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            Files files = filesRepository.findById(id).orElse(new Files());
            if(files.getId() == null || files.getId() == 0) {
                throw new Exception("No se pudo obtener los datos del archivo");
            }
/*
            if(files.getEntityName().equals("activityprogress")){
                ActivityProgress activityProgress = activityProgressRepository.findById(files.getEntityId()).orElse(new ActivityProgress());
                if(activityProgress.getId() == null || activityProgress.getId() == 0) {
                    throw new Exception("No se pudo obtener los datos del registro");
                }
                // Monitor
                if(userPrincipal.getUser().getId().equals(activityProgress.getActivity().getTracing().getUser().getId())){
                    if(!activityProgress.getState().equals("EN REVISION")){
                        throw new Exception("Solo se pueden eliminar archivos de reportes con estado EN REVISIÓN");
                    }
                }
                // Ejecutor
                if(userPrincipal.getUser().getId().equals(activityProgress.getActivity().getUser().getId())){
                    if(!(activityProgress.getState().equals("CREADO") || activityProgress.getState().equals("RECHAZADO"))){
                        throw new Exception("Solo se pueden eliminar archivos de reportes con estado CREADO o RECHAZADO");
                    }
                }

                files.setActive(false);
                filesRepository.save(files);
            }
*/
            files.setActive(false);
            filesRepository.save(files);
        }catch (Exception e){
            message = e.getMessage();
            state = "500";
        }


        JSONObject jo = new JSONObject();
        jo.appendField("status", state);
        jo.appendField("message", message);
        return jo.toString();
    }

    @GetMapping("reportOrganization")
    public String reportFindOrganization(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String ruc,
            @RequestParam(required = false) String internalState,
            @RequestParam(required = false) Integer institutionId,
            @RequestParam(required = false) Integer statusOrganizationId,
            @RequestParam(required = false) Integer typeOrganizationId)
    {
    String status = "200";
    String message;

        try {
            Specification<Organization> spec = OrganizationSpecification.filterByFields(
                   id, name, email, phone, ruc, internalState, institutionId, statusOrganizationId, typeOrganizationId
            );
            List<Organization> organizations = organizationRepository.findAll(spec);

            if (organizations.isEmpty()) {
                throw new Exception("No se encontraron organizaciones");
            }

            message = "Organizaciones encontradas";

            JSONObject jo = createResponse(status, message);
            jo.appendField("organizations", organizations);
            return jo.toString();
        } catch (Exception e) {
            status = "400";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }

    }
    @GetMapping("/search")
    public List<Organization> searchOrganizations(@RequestParam String query) {
        return organizationService.searchOrganizations(query);
    }

    @GetMapping("/organizations")
    public Page<Organization> getOrganizations(@RequestParam String query, Pageable pageable) {
    return organizationRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    @GetMapping("getReportOrganization/{id}")
    public String getTaskMonitorRevision(@PathVariable String id) throws IOException {
        status = "200";
        try {
            int intOrganization = Integer.parseInt(id);
            Organization org =  organizationRepository.findById(intOrganization).orElse(null);
            List<Filial> listFilial = filialRepository.findByOrganizationIdOrderByName(org.getId());
            List<Reform> listReform = reformRepository.findByOrganization(org);
            //List<Member> listMember = memberRepository.findByFilial(listFilial);

            message="Datos encontrados";
            JSONObject jo = createResponse(status,message);
            JSONObject tracingObject = new JSONObject();
            tracingObject.appendField("organization", org);
            tracingObject.appendField("filial", listFilial);
            //tracingObject.appendField("member", listMember);
            tracingObject.appendField("reform", listReform);
            jo.appendField("data", tracingObject);
            return jo.toString();
        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    @GetMapping("filialMatriz/{organizationId}")
    public String findMatrizForFilial(@PathVariable String organizationId) {
        String status = "200";
        String message;

        JSONObject response = new JSONObject();
        try {
            // Validar y convertir el ID de organización
            int intOrgId = Integer.parseInt(organizationId);

            // Buscar la primera filial con matriz = true
            Filial filial = filialRepository.findFirstByOrganizationIdAndMatrixTrue(intOrgId);

            if (filial == null) {
                message = "No tiene una filial registrada como matriz.";
                response.put("result", "null");
            } else {
                message = "Ya tiene registrada una filial como matriz.";
                response.put("result", filial);
            }

            // Crear respuesta final
            response.put("status", status);
            response.put("message", message);

        } catch (NumberFormatException e) {
            // Manejo de error: ID no numérico
            status = "400";
            message = "El ID de la organización debe ser un número válido.";
            response.put("status", status);
            response.put("message", message);
            response.put("error_details", e.getMessage());
        } catch (Exception e) {
            // Manejo de cualquier otra excepción
            status = "500";
            message = "Ocurrió un error al procesar la solicitud.";
            response.put("status", status);
            response.put("message", message);
            response.put("error_details", e.getMessage());
        }

        return response.toString();
    }
    @GetMapping("findLocation/{idFilial}")
    public String findLocationFilial(@PathVariable Integer idFilial) {
        status = "200";
        try {
            List<Location> listLocations = locationRepository.findByFilialId(idFilial);
            message="Datos encontrados";
            JSONObject jo = createResponse(status,message);
            JSONObject tracingObject = new JSONObject();
            tracingObject.appendField("locations", listLocations);
            jo.appendField("data", tracingObject);
            return jo.toString();
        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    @GetMapping("/fRepresentative/{id}")
    public Member fRepresentative(@PathVariable Integer id) {
        Member m = new Member();
        List<Member> l = memberRepository.fRepresentative(id);
        if(!l.isEmpty()){
            m = l.get(0);
        }
        return m;
    }

    @GetMapping("/fPersonByNationalityTypeNumber")
    public Person fPersonByNationalityTypeNumber(@RequestParam("id") int id, @RequestParam("type") String type, @RequestParam("number") String number) {
        DocumentType dt = EnumUtils.getEnum(DocumentType.class, type);
        return personRepository.findFirstByNationalityIdAndDocumentTypeAndDocumentNumber(id, dt, number);
    }

    /*@GetMapping("/fMemberByOrgAndFilial")
    public List<Member> fMemberByOrgAndFilial(@RequestParam("idOrg") Integer idOrg, @RequestParam("IdFilial") Integer IdFilial) {

        return personRepository.findFirstByNationalityIdAndDocumentTypeAndDocumentNumber(id, dt, number);
    }*/
}