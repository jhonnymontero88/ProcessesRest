package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Charge;
import min.gob.ec.tracingservices.model.common.Member;
import min.gob.ec.tracingservices.model.suiosr.Filial;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param; // Importación de @Param
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "member", collectionResourceRel = "tracing")

public interface MemberRepository extends ListCrudRepository<Member, Integer> {
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Member o")
    List<Member> fAllOByName();

    @RestResource(path = "fRepresentative")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Member o where o.status = true and o.filial.matrix = true " +
            "and o.charge.code = 'representante_legal' and o.filial.organization.id = ?1")
    List<Member> fRepresentative(@Param("id") int id);

    @Query("SELECT m FROM member m WHERE m.person.id = :personId AND m.filial.id = :filialId AND m.charge.id = :chargeId")
    Member findByPersonChargeAndFilial(@Param("personId") Integer personId, @Param("filialId") Integer filialId, @Param("chargeId") Integer chargeId);

    List<Member> findByFilialOrganizationId(@Param("id") Integer id);
}
