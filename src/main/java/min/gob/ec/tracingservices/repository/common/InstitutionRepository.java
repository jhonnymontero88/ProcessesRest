package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Institution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "institution", collectionResourceRel = "tracing")
public interface InstitutionRepository extends ListCrudRepository<Institution, Integer> {
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Institution o order by o.name")
    List<Institution> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Institution o where o.status = true order by o.name")
    List<Institution> fAllOByNameActive();

}
