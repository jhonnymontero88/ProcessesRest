package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;

@Entity(name = "filial")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "filial_seq", allocationSize = 1)
public class Filial extends AbstractEntity {

    @Column(nullable = false, length = 2048)
    private String name;

    /*@ManyToOne DEPURACION 2025
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Organization organization;*/

    private boolean matrix;

    private String address;
}
