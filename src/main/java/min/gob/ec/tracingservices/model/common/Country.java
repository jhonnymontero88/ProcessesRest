package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "country")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "country_seq", allocationSize = 1)
public class Country extends AbstractEntity {
    @Column(nullable = false)
    private String name;
}
