package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.StatesSectionDocuments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(path = "statesSectionDocuments", collectionResourceRel = "statesSectionDocuments")
public interface StatesSectionDocumentsRepository extends ListCrudRepository<StatesSectionDocumentsRepository, Integer> {
    @RestResource(path = "fAllOByTipoEstados")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.StatesSectionDocuments o order by o.tipoEstados")
    List<StatesSectionDocumentsRepository> fAllOByTipoEstados();

    @RestResource(path = "fAllOByDocumnentSection")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.StatesSectionDocuments o order by o.documentSection")
    List<StatesSectionDocumentsRepository> fAllOByDocumentSection();

    @RestResource(path = "fAllOByPercentageSection")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.StatesSectionDocuments o order by o.percentageSection")
    List<StatesSectionDocumentsRepository> fAllOByPercentageSection();

    @RestResource(path = "fAllOByAcumulatedPercentage")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.StatesSectionDocuments o order by o.acumulatedPercentage")
    List<StatesSectionDocumentsRepository> fAllOByAcumulatedPercentage();

    @RestResource(path = "gByCoordiantionID")
    List<StatesSectionDocumentsRepository> getByCoordinationIDOrderByName(@Param("id") int id);
}
