package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import jakarta.persistence.*;

@Entity(name = "charge")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "charge_seq", allocationSize = 1)
public class Charge extends AbstractEntity {
    //ES DIRECTIVO SI O NO?
    private boolean directive;
    //ES LIDER RELIGIOSO?
    private boolean religiousLeader;
    @Column(nullable = false)
    private String name;
    private boolean status;
    @Column(nullable = true)
    private String code;

}
