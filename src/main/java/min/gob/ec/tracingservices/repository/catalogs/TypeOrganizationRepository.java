package min.gob.ec.tracingservices.repository.catalogs;

import min.gob.ec.tracingservices.model.catalogs.TypeOrganization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "typeorganization", collectionResourceRel = "tracing")
public interface TypeOrganizationRepository  extends ListCrudRepository<TypeOrganization, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.TypeOrganization o order by o.name")
    List<TypeOrganization> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.TypeOrganization o where o.status = true order by o.name")
    List<TypeOrganization> fAllOByNameActive();
}
