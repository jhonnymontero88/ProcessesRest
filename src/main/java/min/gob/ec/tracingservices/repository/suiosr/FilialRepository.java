package min.gob.ec.tracingservices.repository.suiosr;


import min.gob.ec.tracingservices.model.suiosr.Expedient;
import min.gob.ec.tracingservices.model.suiosr.Filial;
//import min.gob.ec.tracingservices.model.suiosr.Organization; DEPURACION 2025
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RepositoryRestResource(path = "filial", collectionResourceRel = "tracing")
public interface FilialRepository extends ListCrudRepository<Filial, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.suiosr.Filial o")
    Page<Filial> fAllOByName(Pageable pageable);
    Filial findByName(String name);
    //Filial findFirstByOrganizationIdAndMatrixTrue(@Param("id") Integer id);
    //List<Filial> findByOrganizationIdOrderByName(@Param("id") int id);

}
