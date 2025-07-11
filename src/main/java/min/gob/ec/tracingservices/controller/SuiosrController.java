package min.gob.ec.tracingservices.controller;

import min.gob.ec.tracingservices.model.common.*;
import min.gob.ec.tracingservices.model.suiosr.*;
import min.gob.ec.tracingservices.repository.common.*;
import min.gob.ec.tracingservices.repository.suiosr.*;
import min.gob.ec.tracingservices.security.UserPrincipal;
//import min.gob.ec.tracingservices.service.common.OrganizationService; DEPURACION 2025
//import min.gob.ec.tracingservices.util.OrganizationSpecification; DEPURACION 2025
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static min.gob.ec.tracingservices.util.UtilSuiosr.createResponse;

@CrossOrigin
@RestController
@RequestMapping("suiosr")
public class SuiosrController {

    //@Autowired private OrganizationRepository organizationRepository; DEPURACION 2025
    @Autowired private PersonRepository personRepository;
    @Autowired private ContactRepository contactRepository;
    @Autowired private FilialRepository filialRepository;
    @Autowired private ExpedientRepository expedientRepository;
    @Autowired private TracingRepository tracingRepository;
    @Autowired private FilesRepository filesRepository;
    @Autowired private ReformRepository reformRepository;
    @Autowired private LocationRepository locationRepository;
    //@Autowired private OrganizationService organizationService; DEPURACION 2025

    String status = "";
    String message = "";

    // ============================== PERSONA ==============================

    @GetMapping("ci/{documentNumber}")
    public String findByCi(@PathVariable String documentNumber) {
        status = "200";
        try {
            Person person = personRepository.findByCi(documentNumber);
            if (person == null) throw new Exception("Persona no Encontrada");
            message = "Persona encontrada";
            JSONObject jo = createResponse(status, message);
            jo.appendField("person", person);
            return jo.toString();
        } catch (Exception e) {
            status = "404";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.toString());
            return jo.toString();
        }
    }

    @GetMapping("passport/{documentNumber}/{nationality}")
    public String findByPassport(@PathVariable String documentNumber, @PathVariable String nationality) {
        status = "200";
        try {
            Person person = personRepository.findByPassport(documentNumber, nationality);
            if (person == null) throw new Exception("Persona no Encontrada");
            message = "Persona encontrada";
            JSONObject jo = createResponse(status, message);
            jo.appendField("person", person);
            return jo.toString();
        } catch (Exception e) {
            status = "404";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.toString());
            return jo.toString();
        }
    }

    @RequestMapping(value = "/saveperson", method = RequestMethod.POST)
    public String savePerson(@RequestBody Person person) {
        status = "200";
        try {
            Person personSelected = personRepository.findByCi(person.getDocumentNumber());
            if (personSelected != null) {
                throw new Exception("Ya existe persona con ese número de Documento.");
            }
            personRepository.save(person);
            message = "Persona guardada con éxito.";
            JSONObject jo = createResponse(status, message);
            jo.appendField("person", person);
            return jo.toString();
        } catch (Exception e) {
            status = "400";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    //CONTROLADOR PARA CREAR NUEVA ORGANIZACIÓN (PrimerTAB)
    /*@RequestMapping(value = "/saveorganization", method = RequestMethod.POST) // DEPURACION 2025
    public String saveOrganization(@RequestBody Organization organization) {
        try {
            Organization organizationBD = organizationRepository.findFirstByName(organization.getName());
            if (organizationBD != null && !organizationBD.getId().equals(organization.getId())) {
                throw new Exception("Ya existe una organización con el mismo nombre.");
            }

            boolean isNew = organization.getId() == null || organization.getId() <= 0;
            organizationBD = isNew ? new Organization() : organizationRepository.findById(organization.getId()).orElse(new Organization());

            organizationBD.setInstitution(organization.getInstitution());
            organizationBD.setRuc(organization.getRuc());
            organizationBD.setName(organization.getName());
            organizationBD.setEmail(organization.getEmail());
            organizationBD.setPhone(organization.getPhone());
            //organizationBD.setTypeOrganization(organization.getTypeOrganization()); DEPURACION 2025
            //organizationBD.setCurrentReligious(organization.getCurrentReligious()); DEPURACION 2025
            //organizationBD.setStatusOrganization(organization.getStatusOrganization()); DEPUARACION 2025
            organizationBD.setInternalState(organization.getInternalState());
            if (isNew) organizationBD.setRegistrationCompleted(false);

            organizationRepository.save(organizationBD);

            if (isNew) {
                Filial filialMatrix = new Filial();
                filialMatrix.setOrganization(organizationBD);
                filialMatrix.setMatrix(true);
                filialMatrix.setName(organizationBD.getName());
                filialRepository.save(filialMatrix);
            }

            message = "Organización guardada con éxito";
            JSONObject jo = createResponse("200", message);
            jo.appendField("obj", organizationBD);
            return jo.toString();

        } catch (Exception e) {
            message = e.getMessage();
            JSONObject jo = createResponse("400", message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }*/

    // ============================== EXPEDIENTE ==============================
    
    @RequestMapping(value = "/saveexpedient", method = RequestMethod.POST)
    public String saveExpedient(@RequestBody Expedient expedient) {
        status = "200";
        try {
            expedientRepository.save(expedient);
            message = "Expediente guardado con éxito.";
            JSONObject jo = createResponse(status, message);
            //jo.appendField("id_organization", expedient.getOrganization().getId()); DEPURACION 2025
            return jo.toString();
        } catch (Exception e) {
            status = "400";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    // ============================== TRACING ==============================

    //PRUEBAS 2025 DEPURACION
    /*@GetMapping("findTracing/{idOrganizacion}")
    public String getTracing(@PathVariable Integer idOrganizacion) {
        status = "200";
        try {
            List<Tracing> tracings = tracingRepository.fAllOByOrganization(idOrganizacion);
            if (tracings == null) throw new Exception("Error al encontrar Acciones");

            message = "Acciones encontradas";
            JSONObject jo = createResponse(status, message);
            JSONArray tracingsArray = new JSONArray();
            for (Tracing tracing : tracings) {
                JSONObject tracingObject = new JSONObject();
                tracingObject.appendField("creationUser", tracing.getCreationUser());
                tracingObject.appendField("observation", tracing.getObservation());
                tracingObject.appendField("dateAnswer", tracing.getDateAnswer() != null ? tracing.getDateAnswer().toString() : "");
                //tracingObject.appendField("organization", tracing.getOrganization().getName()); DEPURACION 2025
                tracingObject.appendField("creationDate", tracing.getCreationDate() != null ? tracing.getCreationDate().toString() : "");
                tracingObject.appendField("actionsDoneMDG", tracing.getActionsDoneMDG());
                tracingObject.appendField("registered", tracing.isRegistered());
                tracingObject.appendField("documentIn", tracing.getDocumentIn());
                tracingObject.appendField("documentOut", tracing.getDocumentOut());
                tracingObject.appendField("dateRequest", tracing.getDateRequest() != null ? tracing.getDateRequest().toString() : "");
                //tracingObject.appendField("typeprocedure", tracing.getTypeprocedure()); // DEPURACION 2025
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
    //@Transactional(rollbackFor = {RuntimeException.class}) DEPURACION 2025
    public String removeFileFromProgress(@Param("id") Integer id) {
        String message = "Proceso realizado con éxito.";
        String state = "200";

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            Files files = filesRepository.findById(id).orElse(new Files());
            if(files.getId() == null || files.getId() == 0) {
                throw new Exception("No se pudo obtener los datos del archivo");
            }

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

    @GetMapping("getReportOrganization/{id}") // DEPURACION 2025
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
            int intOrgId = Integer.parseInt(organizationId);
            Filial filial = filialRepository.findFirstByOrganizationIdAndMatrixTrue(intOrgId);

            if (filial == null) {
                message = "No tiene una filial registrada como matriz.";
                response.put("result", "null");
            } else {
                message = "Ya tiene registrada una filial como matriz.";
                response.put("result", filial);
            }

            response.put("status", status);
            response.put("message", message);

        } catch (NumberFormatException e) {
            status = "400";
            message = "El ID de la organización debe ser un número válido.";
            response.put("status", status);
            response.put("message", message);
            response.put("error_details", e.getMessage());
        } catch (Exception e) {
            status = "500";
            message = "Ocurrió un error al procesar la solicitud.";
            response.put("status", status);
            response.put("message", message);
            response.put("error_details", e.getMessage());
        }

        return response.toString();
    }*/

    @GetMapping("findLocation/{idFilial}")
    public String findLocationFilial(@PathVariable Integer idFilial) {
        status = "200";
        try {
            List<Location> listLocations = locationRepository.findByFilialId(idFilial);
            message = "Datos encontrados";
            JSONObject jo = createResponse(status, message);
            JSONObject tracingObject = new JSONObject();
            tracingObject.appendField("locations", listLocations);
            jo.appendField("data", tracingObject);
            return jo.toString();
        } catch (Exception e) {
            status = "400";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    // ============================== OTROS ==============================

    @GetMapping("/fPersonByNationalityTypeNumber")
    public Person fPersonByNationalityTypeNumber(@RequestParam("id") int id, @RequestParam("type") String type, @RequestParam("number") String number) {
        DocumentType dt = EnumUtils.getEnum(DocumentType.class, type);
        return personRepository.findFirstByNationalityIdAndDocumentTypeAndDocumentNumber(id, dt, number);
    }
}
