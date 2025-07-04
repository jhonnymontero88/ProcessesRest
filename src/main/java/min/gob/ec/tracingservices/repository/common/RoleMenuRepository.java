package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.RoleMenu;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "rolemenu", collectionResourceRel = "tracing")
public interface RoleMenuRepository extends ListCrudRepository<RoleMenu, Integer> {
    List<RoleMenu> findByRoleId(@Param("roleId") Integer roleId);
}
