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

@Entity(name = "protocols")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "protocols_seq", allocationSize = 1)
public class Protocols extends AbstractEntity{
    @Column(nullable = false)
    private String documenttype;
    @Column(nullable = false)
    private String protocolname;

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
    @JoinColumn(name = "manualfunctionid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private ManualFunction manualfunction;
}


