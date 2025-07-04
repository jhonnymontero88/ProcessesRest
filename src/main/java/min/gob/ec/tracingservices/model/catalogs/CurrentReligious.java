//CATALOGO CREADO
package min.gob.ec.tracingservices.model.catalogs;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.*;

@Entity(name = "currentreligious")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "currentreligious_seq", allocationSize = 1)
public class CurrentReligious extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    private boolean status;

}
