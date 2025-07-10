package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "task", collectionResourceRel = "tracing")
public interface TaskRepository extends ListCrudRepository<Task, Integer> {

    @RestResource(path = "fAllOByName")
    @Query(value = "SELECT t FROM min.gob.ec.tracingservices.model.common.Task t ORDER BY t.entityName")
    List<Task> fAllOByName();
}
