package min.gob.ec.tracingservices.model.processes;

import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.model.common.Profile;

@Entity(name = "userManual")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "userManual", allocationSize = 1)
public class UserManual extends ManualProcesses{
    @Column(nullable = false)
    private String userManualName;

    @ManyToOne 
    @JoinColumn(name = "systemID", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private System system;
    
    @ManyToOne 
    @JoinColumn(name = "profileID", referencedColumnName = "id", nullable = false ) 
    @RestResource(exported = false)
    private Profile profile;
}


