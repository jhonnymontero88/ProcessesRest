package min.gob.ec.tracingservices.repository.common;


import min.gob.ec.tracingservices.model.common.Parish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "parish", collectionResourceRel = "tracing")
public interface ParishRepository extends ListCrudRepository<Parish, Integer> {
//    @RestResource(path = "fAllOByName")
//    List<Parish> findByCantonIdOrderByName(@Param("canton_id") Integer canton_id);

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Parish o order by o.name")
    List<Parish> fAllOByName();

    @RestResource(path = "fAllOByNameActive")
    List<Parish> getByStatusTrueOrderByName();

    @RestResource(path = "gBCanton")
    List<Parish> getByCantonIdOrderByName(@Param("id") int id);
}
