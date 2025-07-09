package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "unit")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "unit_seq", allocationSize = 1)
public class Unit extends AbstractEntity{
    @Column(nullable = false)
    private String delegates;
    @Column(nullable = false)
    private String adress;
    @Column(nullable = false)
    private String macroprocesses;

    @ManyToOne 
    @JoinColumn(name = "coordinationid", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Coordination coordination; 
    
    @ManyToOne 
    @JoinColumn(name = "internalinformationid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Unit unit;
}
