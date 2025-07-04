package min.gob.ec.tracingservices.controller;

import min.gob.ec.tracingservices.model.common.ContactFilial;
import min.gob.ec.tracingservices.model.suiosr.Filial;
import min.gob.ec.tracingservices.repository.common.ContactFilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contactfilialc")
public class ContactFilialController {

    @Autowired
    private ContactFilialRepository contactFilialRepository;

    // Método para obtener todos los contactos de una filial por ID
    /*@GetMapping("findAllByFilial/{filialId}")
    public List<ContactFilial> findAllByFilial(@PathVariable Integer filialId) {
        return contactFilialRepository.findByFilialId(filialId);
    }

    // Método para obtener un único contacto por filial
    @GetMapping("findByFilial/{filialId}")
    public ContactFilial findByFilial(@PathVariable Integer filialId) {
        return contactFilialRepository.findFirstByFilialId(filialId);
    }*/

  
    // Método para guardar un contacto de filial

@PostMapping("save")
public ContactFilial saveContactFilial(@RequestBody ContactFilial contactFilial) {
    // Validar que la filial no sea nula y tenga un ID válido
    Filial filial = contactFilial.getFilial();
    if (filial == null || filial.getId() == null) {
        throw new IllegalArgumentException("La filial debe estar asociada y contener un ID válido.");
    }

    // Guardar el contacto de filial
    return contactFilialRepository.save(contactFilial);
}


    // Método para actualizar un contacto de filial
    @PutMapping("update")
    public ContactFilial updateContactFilial(@RequestBody ContactFilial updatedContactFilial) {
        // Verificar si se proporcionó un ID
        if (updatedContactFilial.getId() == null) {
            throw new IllegalArgumentException("El ID del contacto filial no puede estar vacío");
        }

        // Buscar el contacto filial por ID en la base de datos
        ContactFilial existingContactFilial = contactFilialRepository.findById(updatedContactFilial.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró un contacto filial con el ID " + updatedContactFilial.getId()));

        // Actualizar todos los campos del objeto existente con los nuevos datos
        existingContactFilial.setPhone(updatedContactFilial.getPhone());
        existingContactFilial.setEmail(updatedContactFilial.getEmail());
        existingContactFilial.setFilial(updatedContactFilial.getFilial());

        // Guardar y retornar el contacto de filial actualizado
        return contactFilialRepository.save(existingContactFilial);
    }


    /*@GetMapping("{id}")
    public ContactFilial getContactByFilial(@PathVariable int id) {
        // Llama al repositorio para obtener el ContactFilial
        return contactFilialRepository.findFirstByFilialId(id);
    }*/
}
