package min.gob.ec.tracingservices.model.common;

import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "unit")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "unit", allocationSize = 1)
public class Unit {
    @Column(nullable = false)
    private String delegates;
    @Column(nullable = false)
    private String adress;
    @Column(nullable = false)
    private String macroprocesses;

    @ManyToOne 
    @JoinColumn(name = "coordinationID", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Coordination coordination; 
    
    @ManyToOne 
    @JoinColumn(name = "internalInformationID", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Unit unit;
}
