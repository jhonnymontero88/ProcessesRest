package min.gob.ec.tracingservices.repository.suiosr;

import min.gob.ec.tracingservices.model.common.Parish;
import min.gob.ec.tracingservices.model.common.Person;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "organization", collectionResourceRel = "tracing")
public interface OrganizationRepository extends ListCrudRepository<Organization, Integer> {

    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.suiosr.Organization o order by o.name")
    List<Organization> fAllOByName();

    Organization findFirstByName(String name);

    Organization findFirstByRuc(String ruc);

    @Query("SELECT o FROM min.gob.ec.tracingservices.model.suiosr.Organization o WHERE o.statusOrganization.id IN (3, 4)")
    List<Organization> findByStatus();


    List<Organization> findAll(Specification<Organization> spec);

    List<Organization> findByNameContainingIgnoreCase(String name);
    List<Organization> findByEmailContainingIgnoreCase(String email);
    List<Organization> findByPhoneContainingIgnoreCase(String phone);

    @RestResource(path = "fByRucNameOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.suiosr.Organization o " +
            "where lower(o.ruc) like lower(concat('%',:filter,'%')) " +
            "or lower(o.name) like lower(concat('%',:filter,'%')) " +
            "order by o.name")
    List<Organization> fByRucNameOByName(@Param("filter") String filter);

    @RestResource(path = "getAllPage")
    @Query("select o from min.gob.ec.tracingservices.model.suiosr.Organization o " +
            "where o.id > 0 " +
            "and (" +
            "lower(o.name) like concat('%',:search,'%') " +
            "or lower(o.ruc) like concat('%',:search,'%') " +
            "or lower(o.typeOrganization.name) like concat('%',:search,'%') " +
            "or lower(o.currentReligious.name) like concat('%',:search,'%') " +
            ") " +
            "order by o.name")
    Page<Organization> getAllPage(@Param("search") String search, Pageable pageable);
    
    @RestResource(path = "findByName", rel = "findByName")   
    Page<Organization> findByNameContainingIgnoreCase(@Param("query") String query, Pageable pageable);

}
