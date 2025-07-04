package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Canton;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(path = "canton", collectionResourceRel = "tracing")
public interface CantonRepository extends ListCrudRepository<Canton, Integer> {
//    @RestResource(path = "fAllOByName")
//    List<Canton> findByProvinceIdOrderByName(@Param("province_id") Integer province_id);

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Canton o order by o.name")
    List<Canton> fAllOByName();

    @RestResource(path = "fAllOByNameActive")
    List<Canton> getByStatusTrueOrderByName();

    @RestResource(path = "gByProvince")
    List<Canton> getByProvinceIdOrderByName(@Param("id") int id);
}
