package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "profile")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "profile_seq", allocationSize = 1)
public class Profile extends AbstractEntity{
    @Column(nullable = false)
    private String profiletype;
}


