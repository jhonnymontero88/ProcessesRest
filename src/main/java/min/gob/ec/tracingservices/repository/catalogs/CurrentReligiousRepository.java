package min.gob.ec.tracingservices.repository.catalogs;

import min.gob.ec.tracingservices.model.catalogs.CurrentReligious;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "currentreligious", collectionResourceRel = "tracing")
public interface CurrentReligiousRepository extends ListCrudRepository<CurrentReligious, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.CurrentReligious o order by o.name")
    List<CurrentReligious> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.CurrentReligious o where o.status = true order by o.name")
    List<CurrentReligious> fAllOByNameActive();
}
