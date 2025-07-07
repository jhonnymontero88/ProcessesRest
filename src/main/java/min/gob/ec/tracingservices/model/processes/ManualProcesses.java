package min.gob.ec.tracingservices.model.processes;


import min.gob.ec.tracingservices.model.common.Coordination;
import min.gob.ec.tracingservices.model.common.InternalInformation;
import min.gob.ec.tracingservices.model.common.Unit;

import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "manualProcesses")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "manualProcesses", allocationSize = 1)
public class ManualProcesses {
    @Column(nullable = false) 
    private String documentType; 
    @Column(nullable = false)
    private String processes;
    @Column(nullable = false)
    private String documentName;
    @Column(nullable = false)
    private String subProcessesName;
    @Column(nullable = false)
    private String subPrcesses;

    @ManyToOne 
    @JoinColumn(name = "coordinationID", referencedColumnName = "id", nullable = false ) // union de tablas Coordination con ManualProcesses
    @RestResource(exported = false)
    private Coordination coordination; // todo el contenido del objeto relacionado 

    @ManyToOne 
    @JoinColumn(name = "internalInformationID", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private InternalInformation internalInformation;

    @ManyToOne
    @JoinColumn(name = "unitID", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Unit unit;
}


