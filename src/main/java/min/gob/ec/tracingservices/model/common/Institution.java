package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "institution")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "institution_seq", allocationSize = 1)
public class Institution extends AbstractEntity {
    @Column(nullable = false, length = 1024)
    private String name;
    private boolean status;
}
