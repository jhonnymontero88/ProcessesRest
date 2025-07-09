package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "statessectiondocuments")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "statessectiondocuments_seq", allocationSize = 1)
public class StatesSectionDocuments extends AbstractEntity{
    @Column(nullable = false)
    private String tipoestados;
    @Column(nullable = false)
    private String documentsection;
    @Column(nullable = false)
    private int percentagesection;
    @Column(nullable = false)
    private int acumulatedpercentage;
}


