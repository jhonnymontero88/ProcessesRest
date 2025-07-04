package min.gob.ec.tracingservices.repository.catalogs;

import min.gob.ec.tracingservices.model.catalogs.TypeReform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "typereform", collectionResourceRel = "tracing")

public interface TypeReformRepository  extends ListCrudRepository<TypeReform, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.TypeReform o order by o.name")
    List<TypeReform> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.TypeReform o where o.status = true order by o.name")
    List<TypeReform> fAllOByNameActive();
}

