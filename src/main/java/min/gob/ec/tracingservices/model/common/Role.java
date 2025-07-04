package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;

import jakarta.persistence.*;
@Getter
@Setter
@Entity(name = "role")
public class Role {
    @Id
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;

}
