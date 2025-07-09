package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import java.sql.Date;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;


@Entity(name = "internalinformation")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "internalinformation_seq", allocationSize = 1)
public class InternalInformation extends AbstractEntity{
    @Column(nullable = false) 
    private String responsable; 
    @Column(nullable = false)
    private String backup;
    @Column(nullable = false)
    private String documentrequeriment;
    @Column(nullable = false)
    private Date daterequest;

    @ManyToOne 
    @JoinColumn(name = "coordinationid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Coordination coordination; 
}
