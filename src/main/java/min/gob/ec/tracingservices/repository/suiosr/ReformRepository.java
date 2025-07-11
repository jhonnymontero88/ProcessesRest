package min.gob.ec.tracingservices.repository.suiosr;


import min.gob.ec.tracingservices.model.suiosr.Filial;
//import min.gob.ec.tracingservices.model.suiosr.Organization; DEPURACION 2025
import min.gob.ec.tracingservices.model.suiosr.Reform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource(path = "reform", collectionResourceRel = "tracing")
public interface ReformRepository extends ListCrudRepository<Reform, Integer> {

    @RestResource(path = "fAllOById")
    @Query("select o from min.gob.ec.tracingservices.model.suiosr.Reform o order by o.id")
    List<Reform> fAllOById();
    //List<Reform> findByOrganization(Organization organization); DEPURACION 2025
}


