package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Files;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "files", collectionResourceRel = "tracing")
public interface FilesRepository extends ListCrudRepository<Files, Integer> {
    List<Files> findByEntityNameAndEntityId(@Param("entityName") String entityName,
                                            @Param("entityId") Integer entityId
    );

    List<Files> findByEntityNameAndEntityIdAndActiveTrue(@Param("entityName") String entityName,
                                                         @Param("entityId") Integer entityId
    );
}
