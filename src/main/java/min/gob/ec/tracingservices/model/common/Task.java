package min.gob.ec.tracingservices.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "task")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "task_seq", allocationSize = 1)
public class Task extends AbstractEntity {

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @Column(name = "creation_user", nullable = false, length = 100)
    private String creationUser;

    @Column(name = "date_compliance", nullable = true)
    private Timestamp dateCompliance;

    @Column(name = "date_init", nullable = true)
    private LocalDateTime dateInit;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    @Column(name = "entity_name", nullable = false, length = 150)
    private String entityName;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false)
    private Integer progress;

    @Column(nullable = false)
    private Boolean status;

    @Column(name = "usuario_ejecutor_id", nullable = false)
    private Integer usuarioEjecutorId;

    @Column(name = "usuario_monitoreo_id", nullable = false)
    private Integer usuarioMonitoreoId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @RestResource(exported = false)
    private List<TaskActivityNote> notes;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @RestResource(exported = false)
    private List<TaskActivityProgress> progressList;
}
