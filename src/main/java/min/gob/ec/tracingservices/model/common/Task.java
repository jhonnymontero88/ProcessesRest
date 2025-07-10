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

    @Column(name = "creationdate", nullable = false)
    private Timestamp creationdate;

    @Column(name = "creationuser", nullable = false, length = 100)
    private String creationuser;

    @Column(name = "datecompliance", nullable = true)
    private Timestamp datecompliance;

    @Column(name = "dateinit", nullable = true)
    private LocalDateTime dateinit;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "entityid", nullable = false)
    private Integer entityid;

    @Column(name = "entityname", nullable = false, length = 150)
    private String entityName;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false)
    private Integer progress;

    @Column(nullable = false)
    private Boolean status;

    @Column(name = "usuarioejecutorid", nullable = false)
    private Integer usuarioejecutorid;

    @Column(name = "usuariomonitoreoid", nullable = false)
    private Integer usuariomonitoreoid;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @RestResource(exported = false)
    private List<TaskActivityNote> notes;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @RestResource(exported = false)
    private List<TaskActivityProgress> progressList;
}
