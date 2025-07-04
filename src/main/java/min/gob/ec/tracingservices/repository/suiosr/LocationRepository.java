package min.gob.ec.tracingservices.repository.suiosr;

import min.gob.ec.tracingservices.model.suiosr.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "location", collectionResourceRel = "tracing")
public interface LocationRepository extends ListCrudRepository<Location, Integer> {
    //List<Location> findByPersonId(@Param("personId") Integer personId);
    //Location findFirstByPersonIdAndIsPrincipalIsTrue(@Param("personId") Integer personId);
    @Query("SELECT l FROM location l WHERE l.filial.id = :filialId")
    List<Location> findLocationsByFilialId(@Param("filialId") int filialId);

    List<Location> findByFilialId(int filialId);
}
