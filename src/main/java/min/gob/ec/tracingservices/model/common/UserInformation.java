package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "userinformation")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "unit_seq", allocationSize = 1)
public class UserInformation extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "internalInformationID", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private InternalInformation internalinformation;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private User user;
}