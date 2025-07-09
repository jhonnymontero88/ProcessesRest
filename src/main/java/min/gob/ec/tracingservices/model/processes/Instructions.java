package min.gob.ec.tracingservices.model.processes;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.common.Coordination;
import min.gob.ec.tracingservices.model.common.InternalInformation;
import min.gob.ec.tracingservices.model.common.StatesSectionDocuments;
import min.gob.ec.tracingservices.model.common.Unit;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "instructions")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "instructions_seq", allocationSize = 1)
public class Instructions extends AbstractEntity{
    @Column(nullable = false)
    private String documenttype;
    @Column(nullable = false)
    private String processes;
    @Column(nullable = false)
    private String subprocesses;
    @Column(nullable = false)
    private String instructionname;

    @ManyToOne 
    @JoinColumn(name = "coordinationid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Coordination coordination;

    @ManyToOne 
    @JoinColumn(name = "internalinformationid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private InternalInformation internalinformation;

    @ManyToOne 
    @JoinColumn(name = "unitid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Unit unit;

    @ManyToOne 
    @JoinColumn(name = "statessectiondocumentsid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private StatesSectionDocuments statessectiondocuments;
}


