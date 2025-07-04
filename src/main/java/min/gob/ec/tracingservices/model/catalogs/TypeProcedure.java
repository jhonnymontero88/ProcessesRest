package min.gob.ec.tracingservices.model.catalogs;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;



@Getter
@Setter
@Entity(name = "typeprocedure")
@SequenceGenerator(name = "generic_sequence", sequenceName = "typeprocedure_seq", allocationSize = 1)
public class TypeProcedure extends AbstractEntity {

    @Column(nullable = false)
    private String name;
    private boolean status;
    
}

