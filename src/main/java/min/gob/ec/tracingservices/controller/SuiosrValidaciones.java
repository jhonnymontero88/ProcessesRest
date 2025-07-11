package min.gob.ec.tracingservices.controller;

//import min.gob.ec.tracingservices.repository.suiosr.OrganizationRepository; DEPURACION 2025
//import min.gob.ec.tracingservices.model.suiosr.Organization; DEPURACION 2025
import min.gob.ec.tracingservices.service.common.RestDataService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static min.gob.ec.tracingservices.util.UtilSuiosr.createResponse;


import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("validate")
public class SuiosrValidaciones {

    @Autowired
    private RestDataService restDataService;

    /*@Autowired DEPURACION 2025
    private OrganizationRepository organizationRepository;*/

    String status = "";
    String message = "";

    @GetMapping("ci/{cedula}")
    public String getCI(@PathVariable String cedula) {
        status="200";
        try {
            Map<String, Object> response = restDataService.getCI(cedula);
            message="Datos de cédula obtenido con éxito.";
            JSONObject jo = createResponse(status,message);
            jo.appendField("nombre", response.get("nombre"));
            return jo.toString();
        }catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }

    @GetMapping("/ruc/{ruc}")
    public String getRuc(@PathVariable String ruc) {
        status="200";
        try {
            // Validación: Verificar si el RUC existe en la base de datos
            /*Organization organization = organizationRepository.findFirstByRuc(ruc); DEPURACION 2025
            if (organization != null) {
                throw new Exception("Ya existe una organización con el RUC indicado.");
            }*/

            // Continuar con la consulta al endpoint
            List<Map<String, Object>> responses = restDataService.getRuc(ruc);
            Map<String, Object> response = responses.get(0);
            Map<String, Object> datosPrincipales = (Map<String, Object>) response.get("datosPrincipales");
            List<Map<String, Object>> registros = (List<Map<String, Object>>) datosPrincipales.get("registros");
            for (Map<String, Object> registro : registros) {
                if (registro.get("campo").equals("razonSocial")) {
                    String razonSocial = (String) registro.get("valor");
                    if (razonSocial == null || razonSocial.trim().isEmpty()) {
                        throw new Exception("El RUC no contienen información, revise si el dato registrado es correcto.");
                    }
                    message="Datos del RUC obtenidos con éxito.";
                    JSONObject jo = createResponse(status,message);
                    jo.appendField("razonSocial", razonSocial);
                    return jo.toString();
                }
            }
            status="400";
            message="El RUC no contienen información, revise si el dato registrado es correcto.";
            JSONObject jo = createResponse(status, message);
            jo.appendField("error_details", "No se encontró la razón social");
            return jo.toString();

        } catch (Exception e) {
            status="400";
            message=e.getMessage();
            JSONObject jo = createResponse(status,message);
            jo.appendField("error_details", e.getMessage());
            return jo.toString();
        }
    }
}
