package min.gob.ec.tracingservices.model.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.*;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity(name = "contact")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "contact_seq", allocationSize = 1)
public class Contact extends AbstractEntity {
    //PERSONA
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Person person;
    //TELF
    @Column(nullable = false)
    private String phone;
    //EMAIL
    @Column(nullable = false)
    private String email;
}
