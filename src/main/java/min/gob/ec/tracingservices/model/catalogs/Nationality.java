package min.gob.ec.tracingservices.model.catalogs;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "nationality")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "nationality_seq", allocationSize = 1)
public class Nationality extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    private boolean status;

}
