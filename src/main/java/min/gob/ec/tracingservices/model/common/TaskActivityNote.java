package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity(name = "task_activity_note")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "task_activity_note_seq", allocationSize = 1)
public class TaskActivityNote extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Task task;

    @Column(name = "creation_date", nullable = false)
    private java.sql.Timestamp creationDate;

    @Column(name = "creation_user", nullable = false, length = 100)
    private String creationUser;

    @Column(nullable = false, columnDefinition = "text")
    private String note;
}
