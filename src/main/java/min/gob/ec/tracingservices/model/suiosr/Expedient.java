package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "expedient")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "expedient_seq", allocationSize = 1)
public class Expedient extends AbstractEntity {
    //NUMERO DE EXPEDIENTE
    @Column(nullable = false)
    private String numExpedient;
    //NUMERO DE SERIE
    @Column(nullable = false)
    private String numSeries;
    //NUMERO DE CAJA
    @Column(nullable = false)
    private String numBox;
    //NUMERO DE VOLUMEN
    @Column(nullable = false)
    private String numVolumen;
    //NUMERO DE FOJA
    @Column(nullable = false)
    private String numFoja;
    //ORGANIZACIÓN(agregado)
    /*@OneToOne(fetch = FetchType.EAGER) DEPURACION 2025
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Organization organization;*/ 
}
