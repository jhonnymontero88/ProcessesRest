//CATALOGO CREADO
package min.gob.ec.tracingservices.model.catalogs;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;

@Getter
@Setter
@Entity(name = "statusorganization")
@SequenceGenerator(name = "generic_sequence", sequenceName = "statusorganization_seq", allocationSize = 1)
public class StatusOrganization extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    private boolean status;

}
