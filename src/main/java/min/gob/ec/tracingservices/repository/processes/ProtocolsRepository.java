package min.gob.ec.tracingservices.repository.processes;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import min.gob.ec.tracingservices.model.processes.Protocols;

@RepositoryRestResource(path = "protocols", collectionResourceRel = "tracing")
public interface ProtocolsRepository extends ListCrudRepository<Protocols, Integer>{
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.processes.Protocols o order by o.protocolname")
    List<Protocols> fAllOByName();
} 
