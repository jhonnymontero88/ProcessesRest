package min.gob.ec.tracingservices.audit;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AbstractEntityListener.class)
@Getter
@Setter
/*@DynamicInsert
@DynamicUpdate*/
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generic_sequence")
    private Integer id;
    @Column(nullable = false, updatable = false)
    private Date creationDate;
    @Column(nullable = false, updatable = false)
    private String creationUser;
}
