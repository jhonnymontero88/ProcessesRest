//CATALOGO CREADO
package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "parish")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "parish_seq", allocationSize = 1)
public class Parish extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "canton_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Canton canton;
    private boolean status;
}
