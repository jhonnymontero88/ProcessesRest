package min.gob.ec.tracingservices.controller;

import min.gob.ec.tracingservices.model.common.Charge;
import min.gob.ec.tracingservices.model.common.Contact;
import min.gob.ec.tracingservices.model.common.Files;
import min.gob.ec.tracingservices.model.common.Member;
import min.gob.ec.tracingservices.model.suiosr.Filial;
import min.gob.ec.tracingservices.repository.common.ContactRepository;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import min.gob.ec.tracingservices.repository.common.FilesRepository;
import min.gob.ec.tracingservices.repository.common.MemberRepository;
import min.gob.ec.tracingservices.repository.suiosr.OrganizationRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static min.gob.ec.tracingservices.util.UtilSuiosr.createResponse;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("suiosrupdate")
public class SuiosrUpdateController {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContactRepository contactRepository;

    String status = "";
    String message = "";
    @Autowired
    private FilesRepository filesRepository;


    @PatchMapping("savemember/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Integer id, @RequestBody Map<String, Object> dataMember) {
        status="200";
        try {
            // Obtener el miembro existente por ID
            Member existingMember = memberRepository.findById(id).orElse(null);
            if (existingMember == null) {
                throw new Exception("Miembro no encontrado");
            }
            // Obtener el mapa de datos del miembro
            Map<String, Object> memberMap = (Map<String, Object>) dataMember.get("member");
            // Actualizar los campos que se deben actualizar
            if (memberMap.containsKey("status") && memberMap.get("status") != null) {
                existingMember.setStatus((Boolean) memberMap.get("status"));
            }
            if (memberMap.containsKey("dateInitial") && memberMap.get("dateInitial") != null) {
                String dateInitialString = (String) memberMap.get("dateInitial");
                Date dateInitial = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(dateInitialString);
                existingMember.setDateInitial(dateInitial);
            }
            if (memberMap.containsKey("dateEnd") && memberMap.get("dateEnd") != null) {
                String dateEndString = (String) memberMap.get("dateEnd");
                Date dateEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(dateEndString);
                existingMember.setDateEnd(dateEnd);
            }

            Object filialObject = memberMap.get("filial");
            Map<String, Object> filialMap = (Map<String, Object>) filialObject;
            Filial filialSelected = new Filial();
            filialSelected.setId((Integer) filialMap.get("id"));
            filialSelected.setName((String) filialMap.get("name"));

            Object chargeObject = memberMap.get("charge");
            Map<String, Object> chargeMap = (Map<String, Object>) chargeObject;
            Charge chargeSelected = new Charge();
            chargeSelected.setId((Integer) chargeMap.get("id"));
            chargeSelected.setName((String) chargeMap.get("name"));

            // Verificar si ya existe un miembro con ese cargo y que sea directivo en la misma filial
            // se comenta la verificacion, 11/11/2024 Jessica pide que se pueda registrar mas cargos directivos en una misma filial
           // List<Member> existingMembers = memberRepository.findByFilialAndChargeAndDirectiveTrue(filialSelected, chargeSelected);
            // Si existe un miembro con ese cargo y es directivo, verificar el estado
            //for (Member member : existingMembers) {
            //    if (member.isStatus()) { // Si el miembro está activo
            //        throw new Exception("Ya existe un miembro con cargo directivo en esta filial. No se puede agregar otro miembro con el mismo cargo.");
            //    }
            //}
            // Guardar los cambios
            memberRepository.save(existingMember);
            message="Miembro actualizado con éxito";
            JSONObject jo = createResponse(status,message);
            return ResponseEntity.ok(jo.toString());

        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jo.toString());
        }
    }

    @GetMapping("getMemberByPersonAndFilial/{personId}/{filialId}/{chargeId}")
    public ResponseEntity<?> getMemberByPersonAndFilial(@PathVariable Integer personId, @PathVariable Integer filialId, @PathVariable Integer chargeId) {
        try {
            Member existingMember = memberRepository.findByPersonChargeAndFilial(personId, filialId, chargeId);

            if (existingMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
            }
            return ResponseEntity.ok(existingMember);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching member");
        }
    }

    // DEPURACION 2025
    /*//CONTROLADOR PARA ACTUALIZAR UNA ORGANIZACIÓN
    @RequestMapping(value = "/organization", method = RequestMethod.POST)
    public String updateOrganization (@RequestBody Organization organization){
        status="200";
        try {
            Organization organizationSelected = organizationRepository.findById(organization.getId()).orElse(null);

            if (organization.getName() != null) organizationSelected.setName(organization.getName());
            if (organization.getEmail() != null) organizationSelected.setEmail(organization.getEmail());
            if (organization.getPhone() != null) organizationSelected.setPhone(organization.getPhone());
            if (organization.getInstitution() != null) organizationSelected.setInstitution(organization.getInstitution());
            if (organization.getRuc() != null) organizationSelected.setRuc(organization.getRuc());
            if (organization.getTypeOrganization() != null) organizationSelected.setTypeOrganization(organization.getTypeOrganization());
            if (organization.getStatusOrganization() != null) organizationSelected.setStatusOrganization(organization.getStatusOrganization());
            if (organization.getCurrentReligious() != null) organizationSelected.setCurrentReligious(organization.getCurrentReligious());
            organizationRepository.save(organizationSelected);
            message="Organización actualizada con éxito.";
            JSONObject jo = createResponse(status,message);
            jo.appendField("id_organization", organization.getId());
            return jo.toString();
        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }*/
    
    // CONTROLADOR PARA ACTUALIZAR CONTACTO
    @RequestMapping(value = "/updatecontact", method = RequestMethod.POST)
    public ResponseEntity<String> updateContact(@RequestBody Contact contact) {
        status="200";
        try {
            if (contact.getId() == null) {
                return new ResponseEntity<>("El ID del contacto no puede ser nulo", HttpStatus.BAD_REQUEST);
            }
            Contact existingContact = contactRepository.findById(contact.getId()).orElse(null);
            if (existingContact == null) {
                return new ResponseEntity<>("Contacto no encontrado", HttpStatus.NOT_FOUND);
            }
            existingContact.setEmail(contact.getEmail());
            existingContact.setPhone(contact.getPhone());
            contactRepository.save(existingContact);
            message="Contacto actualziado con éxito";
            JSONObject jo = createResponse(status,message);
            jo.appendField("id", contact.getId());
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
        } catch (Exception e) {
            status="500";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return new ResponseEntity<>(jo.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(@RequestBody Files file) {
        status="200";
        try {
           Files existingFile = filesRepository.findById(file.getId()).orElse(null);
            if (existingFile == null) {
                return new ResponseEntity<>("Archivo no encontrado", HttpStatus.NOT_FOUND);
            }
           // existingFile.setActive(false);
            filesRepository.save(existingFile);
            message="Documento eliminado con éxito";
            JSONObject jo = createResponse(status,message);
            jo.appendField("id", file.getId());
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
        } catch (Exception e) {
            status="500";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return new ResponseEntity<>(jo.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
