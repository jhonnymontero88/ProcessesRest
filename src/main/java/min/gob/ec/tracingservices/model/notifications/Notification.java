package min.gob.ec.tracingservices.model.notifications;

import min.gob.ec.tracingservices.audit.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "notification")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "notification_seq", allocationSize = 1)
public class Notification extends AbstractEntity {
    @Column(nullable = false)
    private String subject;
    private Boolean done;
    private String observation;
}
