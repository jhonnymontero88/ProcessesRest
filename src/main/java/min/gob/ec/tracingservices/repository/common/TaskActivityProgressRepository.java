package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.TaskActivityProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "task-activity-progress", collectionResourceRel = "tracing")
public interface TaskActivityProgressRepository extends ListCrudRepository<TaskActivityProgress, Integer> {

    @RestResource(path = "fAllOByName")
    @Query("SELECT t FROM min.gob.ec.tracingservices.model.common.TaskActivityProgress t ORDER BY t.name")
    List<TaskActivityProgress> fAllOByName();
}
