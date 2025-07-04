package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.suiosr.Filial;
import min.gob.ec.tracingservices.model.suiosr.Organization;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "member")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "member_seq", allocationSize = 1)
public class Member extends AbstractEntity {
    //PERSONA
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Person person;
    //CARGO
    @ManyToOne
    @JoinColumn(name = "charge_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Charge charge;
    //ORGANIZACION
    @ManyToOne
    @JoinColumn(name = "filial_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Filial filial;
    //FECHA DE INICIO A CARGO
    @Column(nullable = true)
    private Date dateInitial;
    //FECHA DE FIN A CARGO
    @Column(nullable = true)
    private Date dateEnd;
    //ESTADO (ACTIVO/INACTIVO)
    @Column(nullable = false)
    private boolean status;
    @Transient
    @RestResource(exported = false)
    private Organization organization;
}
