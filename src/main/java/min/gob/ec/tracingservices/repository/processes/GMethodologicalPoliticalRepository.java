package min.gob.ec.tracingservices.repository.processes;

import min.gob.ec.tracingservices.model.processes.GMethodologicalPolitical;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "gmethodologicalpolitical", collectionResourceRel = "tracing")
public interface GMethodologicalPoliticalRepository extends ListCrudRepository<GMethodologicalPolitical, Integer> {
//    @RestResource(path = "fAllOByName")
//    List<Canton> findByProvinceIdOrderByName(@Param("province_id") Integer province_id);

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.processes.GMethodologicalPolitical o order by o.namepoliticalguide")
    List<GMethodologicalPolitical> fAllOByName();
}
