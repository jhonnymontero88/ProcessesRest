package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.suiosr.Tracing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "tracing", collectionResourceRel = "tracing")
public interface TracingRepository extends ListCrudRepository<Tracing, Integer> {

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.suiosr.Tracing o order by o.observation")
    List<Tracing> fAllOByName();

    /*@RestResource(path = "fAllOByOrganization") // DEPRACION 2025
    @Query(value = "select o from min.gob.ec.tracingservices.model.suiosr.Tracing o where o.organization.id = :idOrganization order by o.observation")
    List<Tracing> fAllOByOrganization(int idOrganization);*/
}
