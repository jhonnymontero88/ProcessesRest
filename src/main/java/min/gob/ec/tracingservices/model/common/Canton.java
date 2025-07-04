//CATALOGO MODIFICADO
package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "canton")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "canton_seq", allocationSize = 1)
public class Canton extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Province province;
    private boolean status;
}
