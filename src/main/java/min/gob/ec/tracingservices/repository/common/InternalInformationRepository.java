package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.InternalInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "internalinformation", collectionResourceRel = "traciong")
public interface InternalInformationRepository extends ListCrudRepository<InternalInformation, Integer>{
    
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.InternalInformation o order by o.responsable")
    List<InternalInformation> fAllOByName();
    
}
