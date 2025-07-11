package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;

@Entity(name = "location")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "location_seq", allocationSize = 1)
public class Location extends AbstractEntity {

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "filial_id", referencedColumnName = "id", nullable = true)
    @RestResource(exported = false)
    private Filial filial;
}
