package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "country", collectionResourceRel = "tracing")
public interface CountryRepository extends ListCrudRepository<Country, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Country o order by o.name")
    List<Country> fAllOByName();
}
