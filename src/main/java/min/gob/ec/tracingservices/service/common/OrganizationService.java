package min.gob.ec.tracingservices.service.common;

import jakarta.persistence.PersistenceContext;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import min.gob.ec.tracingservices.repository.suiosr.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> searchOrganizations(String query) {
        if (query.length() < 3) {
            return List.of(); // Retorna una lista vacía si la consulta tiene menos de 3 caracteres
        }

        // Realiza la búsqueda en diferentes campos
        List<Organization> organizationsByName = organizationRepository.findByNameContainingIgnoreCase(query);
        List<Organization> organizationsByEmail = organizationRepository.findByEmailContainingIgnoreCase(query);
        List<Organization> organizationsByPhone = organizationRepository.findByPhoneContainingIgnoreCase(query);

        // Combina los resultados (puedes usar un Set para evitar duplicados)
        organizationsByName.addAll(organizationsByEmail);
        organizationsByName.addAll(organizationsByPhone);

        return organizationsByName;
    }


}
