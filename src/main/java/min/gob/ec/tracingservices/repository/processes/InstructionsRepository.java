package min.gob.ec.tracingservices.repository.processes;

import min.gob.ec.tracingservices.model.processes.Instructions;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "instructions", collectionResourceRel = "tracing")

public interface InstructionsRepository extends ListCrudRepository<Instructions, Integer>{
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.processes.Instructions o order by o.instructionname")
    List<Instructions> fAllOByName();
}
