package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
//import min.gob.ec.tracingservices.model.catalogs.TypeProcedure; DEPURACION 2025

import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity(name = "tracing")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "tracing_seq", allocationSize = 1)
public class Tracing extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Organization organization;

    /*// DEPURACION 2025 
    @ManyToOne
    @JoinColumn(name = "procedure_id", referencedColumnName = "id", nullable = true)
    @RestResource(exported = false)
    private TypeProcedure typeprocedure;*/

    // Solicitado por
    @Column(nullable = false, length = 2048)
    private String observation;

    @Column(nullable = false)
    private String documentIn;

    private String actionsDoneMDG;

    @Column(nullable = false)
    private boolean registered;

    private String documentOut;

    // Fecha solicitud
    @Column(nullable = true)
    private OffsetDateTime  dateAnswer;
    @Column(nullable = true)
    private OffsetDateTime dateRequest;
}
