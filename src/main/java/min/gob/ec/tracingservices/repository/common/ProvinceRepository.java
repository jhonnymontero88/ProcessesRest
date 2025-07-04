package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Province;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "province", collectionResourceRel = "tracing")
public interface ProvinceRepository extends ListCrudRepository<Province, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Province o order by o.name")
    List<Province> fAllOByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Province o where o.status = true order by o.name")
    List<Province> fAllOByNameActive();
}
