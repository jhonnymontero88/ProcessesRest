package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.StatesSectionDocuments;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "statessectiondocuments", collectionResourceRel = "tracing")
public interface StatesSectionDocumentsRepository extends ListCrudRepository<StatesSectionDocuments, Integer>{

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.StatesSectionDocuments o order by o.tipoestados")
    List<StatesSectionDocuments> fAllOByName();

}