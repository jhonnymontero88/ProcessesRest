package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.catalogs.CurrentReligious;
import min.gob.ec.tracingservices.model.common.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "user", collectionResourceRel = "tracing")
public interface UserRepository extends ListCrudRepository<User, Integer> {
    List<User> findByEmail(String email);
    List<User> findByActiveTrue();
    List<User> findByIdIn(List<Integer> ids);
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.User o order by o.name")
    List<User> getAllOrderByName();

   
}
