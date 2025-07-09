package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "system")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "system_seq", allocationSize = 1)
public class System extends AbstractEntity{
    @Column(nullable = false)
    private String system;
    @Column(nullable = false)
    private String module;
}


