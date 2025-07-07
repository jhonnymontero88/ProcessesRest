package min.gob.ec.tracingservices.model.processes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "instructions")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "instructions", allocationSize = 1)
public class Instructions extends ManualProcesses{
    @Column(nullable = false)
    private String processes;
    private String subprocesses;
    private String instructionName;
}


