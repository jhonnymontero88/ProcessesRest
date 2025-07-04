package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.ContactFilial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "contactfilial", collectionResourceRel = "tracing")
public interface ContactFilialRepository extends ListCrudRepository<ContactFilial, Integer> {

    /*@Query("SELECT c FROM contactfilial c WHERE c.filial.id = :filialId")
    List<ContactFilial> findAllByFilial(@Param("filialId") int idFilial);*/
    // @RestResource(path = "findAllByFilial")
    List<ContactFilial> findByFilialId(@Param("idFilial") int idFilial);

    /*@Query(value = "SELECT * FROM contactfilial WHERE filial_id = ?1 LIMIT 1", nativeQuery = true)
    ContactFilial findByFilial(int idFilial);*/
    // @RestResource(path = "findByFilial")
    ContactFilial findFirstByFilialId(@Param("idFilial") int idFilial);
}
