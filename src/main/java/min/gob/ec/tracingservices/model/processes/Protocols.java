package min.gob.ec.tracingservices.model.processes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "protocols")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "protocols", allocationSize = 1)
public class Protocols extends ManualFunction{
    @Column(nullable = false)
    private String protocolName;
}


