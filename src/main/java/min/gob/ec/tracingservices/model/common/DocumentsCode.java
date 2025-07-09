package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.processes.GMethodologicalPolitical;
import min.gob.ec.tracingservices.model.processes.Instructions;
import min.gob.ec.tracingservices.model.processes.ManualFunction;
import min.gob.ec.tracingservices.model.processes.ManualProcesses;
import min.gob.ec.tracingservices.model.processes.Protocols;
import min.gob.ec.tracingservices.model.processes.UserManual;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "documentscode")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "documentscode_seq", allocationSize = 1)
public class DocumentsCode extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "manualprocessesid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private ManualProcesses manualProcesses;

    @ManyToOne
    @JoinColumn(name = "instructionsid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Instructions instructions;

    @ManyToOne
    @JoinColumn(name = "usermanualid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private UserManual usermanual;

    @ManyToOne
    @JoinColumn(name = "manualfunctionalityid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private ManualFunction manualfunction;

    @ManyToOne
    @JoinColumn(name = "protocolsid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Protocols protocols;

    @ManyToOne
    @JoinColumn(name = "gmethodologicalpoliticalid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private GMethodologicalPolitical gmethodologicalpolitical;

    @Column(nullable = false)
    private String sequential;
    @Column(nullable = false)
    private Integer year;
    
}