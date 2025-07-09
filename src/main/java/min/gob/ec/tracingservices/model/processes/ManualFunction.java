package min.gob.ec.tracingservices.model.processes;

import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.common.Coordination;
import min.gob.ec.tracingservices.model.common.InternalInformation;
import min.gob.ec.tracingservices.model.common.StatesSectionDocuments;
import min.gob.ec.tracingservices.model.common.Unit;

@Entity(name = "manualfunction")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "manualfunction_seq", allocationSize = 1)
public class ManualFunction extends AbstractEntity{
    @Column(nullable = false)
    private String documenttype;
    @Column(nullable = false)
    private String manualfunctionsname;
    
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

    @ManyToOne 
    @JoinColumn(name = "manualprocessesid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private ManualProcesses manualprocesses;

    @ManyToOne 
    @JoinColumn(name = "systemid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private System system;
}


