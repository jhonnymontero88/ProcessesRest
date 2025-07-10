package min.gob.ec.tracingservices.repository.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import min.gob.ec.tracingservices.model.common.DocumentsCode;

import java.util.List;

@RepositoryRestResource(path = "documentscode", collectionResourceRel = "tracing")
public interface DocumentsCodeRepository extends ListCrudRepository<DocumentsCode, Integer>{

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.DocumentsCode o order by o.sequential")
    List<DocumentsCode> fAllOByName();

    
}