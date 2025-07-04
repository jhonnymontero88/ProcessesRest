package min.gob.ec.tracingservices.repository.catalogs;
import min.gob.ec.tracingservices.model.catalogs.TypeProcedure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "typeprocedure", collectionResourceRel = "tracing")

public interface TypeProcedureRepository extends ListCrudRepository<TypeProcedure, Integer> {
    
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.TypeProcedure o order by o.name")
    List<TypeProcedure> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.catalogs.TypeProcedure o where o.status = true order by o.name")
    List<TypeProcedure> fAllOByNameActive();
}
