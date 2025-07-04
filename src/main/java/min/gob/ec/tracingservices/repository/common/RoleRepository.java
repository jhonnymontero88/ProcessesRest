package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(path = "role", collectionResourceRel = "tracing")
public interface RoleRepository extends ListCrudRepository<Role, Integer> {
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Role o order by o.name")
    List<Role> getAllOrderByName();

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Role o where o.active = true order by o.name")
    List<Role> fAllOByNameActive();
}
