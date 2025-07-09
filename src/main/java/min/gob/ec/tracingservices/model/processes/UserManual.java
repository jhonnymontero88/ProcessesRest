package min.gob.ec.tracingservices.model.processes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.model.common.SystemEntity;
import min.gob.ec.tracingservices.model.common.Unit;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.common.Coordination;
import min.gob.ec.tracingservices.model.common.InternalInformation;
import min.gob.ec.tracingservices.model.common.Profile;
import min.gob.ec.tracingservices.model.common.StatesSectionDocuments;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity(name = "usermanual")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "usermanual_seq", allocationSize = 1)
public class UserManual extends AbstractEntity{
    @Column(nullable = false)
    private String documenttype;
    @Column(nullable = false)
    private String usermanualname;

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
    @JoinColumn(name = "systemid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private SystemEntity system;
    
    @ManyToOne 
    @JoinColumn(name = "profileid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Profile profile;
}


