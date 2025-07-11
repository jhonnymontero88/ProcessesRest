package min.gob.ec.tracingservices.controller;

import min.gob.ec.tracingservices.model.common.Contact;
import min.gob.ec.tracingservices.model.common.Files;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import min.gob.ec.tracingservices.repository.common.ContactRepository;
import min.gob.ec.tracingservices.repository.common.FilesRepository;
import min.gob.ec.tracingservices.repository.suiosr.OrganizationRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static min.gob.ec.tracingservices.util.UtilSuiosr.createResponse;

@CrossOrigin
@RestController
@RequestMapping("suiosrupdate")
public class SuiosrUpdateController {

    @Autowired private OrganizationRepository organizationRepository;
    @Autowired private ContactRepository contactRepository;
    @Autowired private FilesRepository filesRepository;

    String status = "";
    String message = "";

    // POST - ACTUALIZAR ORGANIZACIÓN
    @RequestMapping(value = "/organization", method = RequestMethod.POST)
    public String updateOrganization(@RequestBody Organization organization) {
        status = "200";
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

            message = "Organización actualizada con éxito.";
            JSONObject jo = createResponse(status, message);
            jo.appendField("id_organization", organization.getId());
            return jo.toString();

        } catch (Exception e) {
            status = "400";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    // POST - ACTUALIZAR CONTACTO
    @RequestMapping(value = "/updatecontact", method = RequestMethod.POST)
    public ResponseEntity<String> updateContact(@RequestBody Contact contact) {
        status = "200";
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

            message = "Contacto actualizado con éxito";
            JSONObject jo = createResponse(status, message);
            jo.appendField("id", contact.getId());
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

        } catch (Exception e) {
            status = "500";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return new ResponseEntity<>(jo.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST - ELIMINAR ARCHIVO
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(@RequestBody Files file) {
        status = "200";
        try {
            Files existingFile = filesRepository.findById(file.getId()).orElse(null);
            if (existingFile == null) {
                return new ResponseEntity<>("Archivo no encontrado", HttpStatus.NOT_FOUND);
            }

            // existingFile.setActive(false); // Si deseas desactivar en vez de borrar
            filesRepository.save(existingFile);

            message = "Documento eliminado con éxito";
            JSONObject jo = createResponse(status, message);
            jo.appendField("id", file.getId());
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

        } catch (Exception e) {
            status = "500";
            message = e.getMessage();
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", e.getMessage());
            return new ResponseEntity<>(jo.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
