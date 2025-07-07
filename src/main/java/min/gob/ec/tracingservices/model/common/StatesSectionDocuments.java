package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "statesSectionDocuments")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "statesSectionDocuments", allocationSize = 1)
public class StatesSectionDocuments {
    @Column(nullable = false)
    private String tipoEstados;
    @Column(nullable = false)
    private String documentSection;
    @Column(nullable = false)
    private int percentageSection;
    @Column(nullable = false)
    private int acumulatedPercentage;
}


