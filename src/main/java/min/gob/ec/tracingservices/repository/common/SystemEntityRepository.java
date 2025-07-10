package min.gob.ec.tracingservices.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import min.gob.ec.tracingservices.model.common.SystemEntity;

@RepositoryRestResource(path = "systementity", collectionResourceRel = "tracing")
public interface SystemEntityRepository extends ListCrudRepository<SystemEntity, Integer>{
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.SystemEntity o order by o.system")
    List<SystemEntity> fAllOByName();
}
