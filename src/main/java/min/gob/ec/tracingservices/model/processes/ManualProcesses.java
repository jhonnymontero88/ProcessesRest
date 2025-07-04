package min.gob.ec.tracingservices.model.processes
import lombok.Getter;
import lombok.Setter;
import org.springframeork.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "manualProcesses")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "manualProcesses", allocationSize = 1)
public class ManualProcesses {
    @Column(nullable=false)
    private String documentType;
    @ManyToOne
    @JoinColumn(name = "cordinationID", referencedColumnName = "id", nullable = false )
    @RestResource(exported = false)
    private Coordination coordination;
    
}


