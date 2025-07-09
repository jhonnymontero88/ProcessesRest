package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity(name = "taskactivitynote")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "task_activity_note_seq", allocationSize = 1)
public class TaskActivityNote extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "taskid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Task task;

    @Column(name = "creationdate", nullable = false)
    private java.sql.Timestamp creationdate;

    @Column(name = "creationuser", nullable = false, length = 100)
    private String creationuser;

    @Column(nullable = false, columnDefinition = "text")
    private String note;
}
