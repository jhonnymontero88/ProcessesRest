package min.gob.ec.tracingservices.model.processes;

import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.common.Coordination;
import min.gob.ec.tracingservices.model.common.InternalInformation;
import min.gob.ec.tracingservices.model.common.Unit;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "manualprocesses")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "manualprocesses_seq", allocationSize = 1)
public class ManualProcesses extends AbstractEntity{
    @Column(nullable = false) 
    private String documenttype; 
    @Column(nullable = false)
    private String processes;
    @Column(nullable = false)
    private String documentname;
    @Column(nullable = false)
    private String subprocessesname;
    @Column(nullable = false)
    private String subprcesses;

    @ManyToOne 
    @JoinColumn(name = "coordinationid", referencedColumnName = "id", nullable = false ) // union de tablas Coordination con ManualProcesses
    @RestResource(exported = false)
    private Coordination coordination; // todo el contenido del objeto relacionado 

    @ManyToOne 
    @JoinColumn(name = "internalInformationid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private InternalInformation internalInformation;

    @ManyToOne
    @JoinColumn(name = "unitid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Unit unit;
}


