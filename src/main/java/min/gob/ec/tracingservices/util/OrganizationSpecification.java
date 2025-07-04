package min.gob.ec.tracingservices.util;

import min.gob.ec.tracingservices.model.suiosr.Organization;
import org.springframework.data.jpa.domain.Specification;

public class OrganizationSpecification {
    public static Specification<Organization> filterByFields(
            Integer id,
            String name,
            String email,
            String phone,
            String ruc,
            String internalState,
            Integer institutionId,  // Suponiendo que este es el ID de Institution
            Integer statusOrganizationId,  // Suponiendo que este es el ID de StatusOrganization
            Integer typeOrganizationId) {  // Suponiendo que este es el ID de TypeOrganization

        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            // Filtros para atributos de Organization
            if (name != null && !name.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("phone"), phone));
            }
            if (ruc != null && !ruc.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("Ruc"), ruc));
            }
            if (internalState != null && !internalState.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("internalState"), internalState));
            }

            // Filtro id
            if (id != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("id").get("id"), id));
            }
            // Filtros para claves foráneas
            if (institutionId != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("institution").get("id"), institutionId));
            }
            if (statusOrganizationId != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("statusOrganization").get("id"), statusOrganizationId));
            }
            if (typeOrganizationId != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("typeOrganization").get("id"), typeOrganizationId));
            }

            return predicates;
        };
    }
}
