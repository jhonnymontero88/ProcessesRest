package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.TaskActivityNote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "task-activity-note", collectionResourceRel = "tracing")
public interface TaskActivityNoteRepository extends ListCrudRepository<TaskActivityNote, Integer> {

    @RestResource(path = "fAllOByName")
    @Query("SELECT t FROM min.gob.ec.tracingservices.model.common.TaskActivityNote t ORDER BY t.note")
    List<TaskActivityNote> fAllOByName();
}
