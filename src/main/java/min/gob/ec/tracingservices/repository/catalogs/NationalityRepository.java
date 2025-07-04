package min.gob.ec.tracingservices.repository.catalogs;


import min.gob.ec.tracingservices.model.catalogs.Nationality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "nationality", collectionResourceRel = "tracing")
public interface NationalityRepository extends ListCrudRepository<Nationality, Integer> {

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.Nationality o order by o.name")
    List<Nationality> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.Nationality o where o.status = true order by o.name")
    List<Nationality> fAllOByNameActive();
}
