package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Unit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "unit", collectionResourceRel = "tracing")
public interface UnitRepository extends ListCrudRepository <Unit, Integer>{

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Unit o order by o.adress")
    List<Unit> fAllOByName();
    
}
