package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "secretariat")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName ="secretiriat_sql", allocationSize = 1)
public class Secretariat extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "coordinationID", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Coordination coordination;
    
}