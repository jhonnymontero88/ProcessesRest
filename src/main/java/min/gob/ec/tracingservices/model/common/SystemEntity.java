package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "systemEntity")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "systemEntity_seq", allocationSize = 1)
public class SystemEntity extends AbstractEntity{
    @Column(nullable = false)
    private String system;
    @Column(nullable = false)
    private String module;
}


