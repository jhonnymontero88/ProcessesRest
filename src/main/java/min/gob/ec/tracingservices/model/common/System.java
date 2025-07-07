package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "system")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "system", allocationSize = 1)
public class System {
    @Column(nullable = false)
    private String system;
    private String module;
}


