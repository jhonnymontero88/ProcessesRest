package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;


@Entity(name = "taskactivityprogress")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "task_activity_progress_seq", allocationSize = 1)
public class TaskActivityProgress extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "taskid", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Task task;

    @Column(name = "creationdate", nullable = false)
    private java.sql.Timestamp creationDate;

    @Column(name = "creationuser", nullable = false, length = 100)
    private String creationUser;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Integer progress;

    @Column(nullable = false, length = 50)
    private String state;
}
