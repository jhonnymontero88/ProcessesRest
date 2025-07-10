package min.gob.ec.tracingservices.repository.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import min.gob.ec.tracingservices.model.common.Secretariat;

import java.util.List;


@RepositoryRestResource(path = "secretariat", collectionResourceRel = "tracing")
public interface SecretariatRepository extends ListCrudRepository<Secretariat, Integer>{

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Secretariat o order by o.name")
    List<Secretariat> fAllOByName();

}