package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Charge;
import min.gob.ec.tracingservices.model.suiosr.Filial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "charge", collectionResourceRel = "tracing")

public interface ChargeRepository extends ListCrudRepository<Charge, Integer> {
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Charge o order by o.name")
    List<Charge> fAllOByName();

    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Charge o where o.name = ?1")
    Charge findByName(String name);

    Charge findChargeByCode(String code);

    //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Charge o where o.status = true order by o.name")
    List<Charge> fAllOByNameActive();



    Charge findById(int id);

}
