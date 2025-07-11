package min.gob.ec.tracingservices.model.common;

import min.gob.ec.tracingservices.audit.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "files")
@SequenceGenerator(name = "generic_sequence", sequenceName = "files_seq", allocationSize = 1)
public class Files extends AbstractEntity {

    @Column(nullable = false)
    private Integer entityid;

    @Column(nullable = false)
    private String entityname;

    @Column(nullable = false)
    private String filename;

    @JsonIgnore
    private String path;

    @Column(columnDefinition = "boolean default true")
    private Boolean active;
}
