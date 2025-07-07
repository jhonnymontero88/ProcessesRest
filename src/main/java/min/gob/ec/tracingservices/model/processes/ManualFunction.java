package min.gob.ec.tracingservices.model.processes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "manualFunction")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "manualFunction", allocationSize = 1)
public class ManualFunction extends UserManual{
    @Column(nullable = false)
    private String manualFunctionsName;
}


