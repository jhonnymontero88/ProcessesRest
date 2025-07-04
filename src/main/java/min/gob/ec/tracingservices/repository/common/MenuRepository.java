package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Menu;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "menu", collectionResourceRel = "tracing")
public interface MenuRepository extends ListCrudRepository<Menu, Integer> {
    List<Menu> findByActiveTrue();
    List<Menu> findByIdIn(List<Integer> ids);
    List<Menu> findByParent(@Param("parent") Integer parent);
}
