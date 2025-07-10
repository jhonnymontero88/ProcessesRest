package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Coordination;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "coordination", collectionResourceRel = "tracing")
public interface CoordinationRepository extends ListCrudRepository<Coordination, Integer>{
    
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Coordination o order by o.area")
    List<Coordination> fAllOByName();

}
