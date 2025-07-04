package min.gob.ec.tracingservices.repository.suiosr;

import min.gob.ec.tracingservices.model.suiosr.Expedient;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "expedient", collectionResourceRel = "tracing")
public interface ExpedientRepository extends ListCrudRepository<Expedient, Integer> {

    @RestResource(path = "findByOrganization")
    Expedient findFirstByOrganizationId(@Param("id") int id);

    @Query(value = "select o from min.gob.ec.tracingservices.model.suiosr.Expedient o where o.id = ?1")
    Expedient findById(int id);

}
