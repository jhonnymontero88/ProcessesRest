package min.gob.ec.tracingservices.repository.catalogs;

import min.gob.ec.tracingservices.model.catalogs.StatusOrganization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "statusorganization", collectionResourceRel = "tracing")
public interface StatusOrganizationRepository  extends ListCrudRepository<StatusOrganization, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.StatusOrganization o order by o.name")
    List<StatusOrganization> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.StatusOrganization o where o.status = true order by o.name")
    List<StatusOrganization> fAllOByNameActive();
}
