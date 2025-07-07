package min.gob.ec.tracingservices.model.common;

import java.sql.Date;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "internalInformation")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "internalInformation", allocationSize = 1)
public class InternalInformation {
    @Column(nullable = false) 
    private String responsable; 
    @Column(nullable = false)
    private String backup;
    @Column(nullable = false)
    private String documentRequeriment;
    @Column(nullable = false)
    private Date dateRequest;

    @ManyToOne 
    @JoinColumn(name = "coordinationID", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Coordination coordination; 
}
