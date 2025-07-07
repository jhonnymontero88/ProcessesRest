package min.gob.ec.tracingservices.model.processes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "gMethodologicalPolitical")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "gMethodologicalPolitical", allocationSize = 1)
public class GMethodologicalPolitical extends ManualProcesses{
    @Column(nullable = false)
    private String namePoliticalGuide;
}