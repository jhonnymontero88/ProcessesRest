package min.gob.ec.tracingservices.repository.common;


import min.gob.ec.tracingservices.model.common.DocumentType;
import min.gob.ec.tracingservices.model.common.Person;
import min.gob.ec.tracingservices.model.common.User;
//import min.gob.ec.tracingservices.model.suiosr.Organization; DEPURACION 2025
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "person", collectionResourceRel = "tracing")
public interface PersonRepository extends ListCrudRepository<Person, Integer> {
    List<Person> findByIdNotIn(List<Integer> ids);
    List<Person> findByDocumentNumber(String documentNumber);
    Person findByDocumentNumberEquals(String documentNumber);
    //
    @RestResource(path = "fAllOByName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Person o order by o.lastName, o.firstName")
    List<Person> fAllOByName();

    @RestResource(path = "fByLNameFNameOByLNameFName")
    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Person o " +
            "where lower(o.lastName) like lower(concat('%',:filter,'%')) " +
            "or lower(o.firstName) like lower(concat('%',:filter,'%')) " +
            "or lower(o.documentNumber) like lower(concat('%',:filter,'%')) " +
            "order by o.lastName, o.firstName")
    List<Person> fByLNameFNameOByLNameFName(@Param("filter") String filter);


    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Person o where o.documentNumber = ?1")
    Person findByCi(String documentNumber);

    @Query(value = "select o from min.gob.ec.tracingservices.model.common.Person o where o.documentType = 'PASAPORTE' and o.documentNumber = ?1 and o.nationality.name = ?2")
    Person findByPassport(String documentNumber, String nationality);


      //LISTAR SOLO ACTIVOS
    @RestResource(path = "fAllOByNameActive")
        @Query(value = "select o from min.gob.ec.tracingservices.model.common.Person o where o.status = true order by o.lastName, o.firstName")
    List<Person> fAllOByNameActive();
    @RestResource(path = "fByNationalityTypeNumber")
    Person findFirstByNationalityIdAndDocumentTypeAndDocumentNumber(@Param("id") int id, @Param("type") DocumentType type, @Param("number") String number);

}
