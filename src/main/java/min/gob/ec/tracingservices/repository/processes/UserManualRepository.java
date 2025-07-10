package min.gob.ec.tracingservices.repository.processes;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import min.gob.ec.tracingservices.model.processes.UserManual;

@RepositoryRestResource(path = "usermanual", collectionResourceRel = "tracing")
public interface UserManualRepository extends ListCrudRepository<UserManual, Integer>{
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.processes.UserManual o order by o.usermanualname")
    List<UserManual> fAllOByName();
}
