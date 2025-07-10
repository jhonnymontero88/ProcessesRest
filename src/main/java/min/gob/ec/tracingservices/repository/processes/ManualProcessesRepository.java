package min.gob.ec.tracingservices.repository.processes;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import min.gob.ec.tracingservices.model.processes.ManualProcesses;

@RepositoryRestResource(path = "manualprocesses", collectionResourceRel = "tracing")
public interface ManualProcessesRepository extends ListCrudRepository<ManualProcesses, Integer>{
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.processes.ManualProcesses o order by o.documentname")
    List<ManualProcesses> fAllOByName();
}