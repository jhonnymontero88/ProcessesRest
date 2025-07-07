package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Coordination;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(path = "coordination", collectionResourceRel = "coordinations")
public interface CoordinationRepository extends ListCrudRepository<Coordination, Integer> {
    @RestResource(path = "fAllOByArea")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Coordiantion o order by o.area")
    List<Coordination> fAllOByArea();

    @RestResource(path = "fAllOByCoordSubse")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Coordiantion o order by o.coordSubse")
    List<Coordination> fAllOByCoordSubse();

    /*@RestResource(path = "gByStatesSectionDocuments")
    List<Coordination> getByProvinceIdOrderByName(@Param("id") int id);*/
}
