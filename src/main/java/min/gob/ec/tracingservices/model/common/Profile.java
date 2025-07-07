package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "profile")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "profile", allocationSize = 1)
public class Profile {
    @Column(nullable = false)
    private String profileType;
}


