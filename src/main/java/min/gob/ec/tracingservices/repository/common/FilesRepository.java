package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Files;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "files", collectionResourceRel = "tracing")
public interface FilesRepository extends ListCrudRepository<Files, Integer> {
    
    List<Files> findByEntitynameAndEntityid(@Param("entityname") String entityname,
                                            @Param("entityid") Integer entityid);

    List<Files> findByEntitynameAndEntityidAndActiveTrue(@Param("entityname") String entityname,
                                                         @Param("entityid") Integer entityid);
}
