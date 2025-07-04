package min.gob.ec.tracingservices.repository.common;

import min.gob.ec.tracingservices.model.common.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "contact", collectionResourceRel = "tracing")
public interface ContactRepository extends ListCrudRepository<Contact, Integer> {
    List<Contact> findByPersonId(@Param("id") Integer id);
}
