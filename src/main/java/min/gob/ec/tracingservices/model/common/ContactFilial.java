package min.gob.ec.tracingservices.model.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.suiosr.Filial;
import jakarta.persistence.*;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity(name = "contactfilial")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "contactfilial_seq", allocationSize = 1)
public class ContactFilial extends AbstractEntity {
    //Filial
    @ManyToOne
    @JoinColumn(name = "filial_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Filial filial;
    //TELF
    @Column
    private String phone;
    //EMAIL
    @Column
    private String email;
}
