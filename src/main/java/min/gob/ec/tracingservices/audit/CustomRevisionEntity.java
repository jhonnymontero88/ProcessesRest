package min.gob.ec.tracingservices.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "zaud_zinfo")
public class CustomRevisionEntity extends DefaultRevisionEntity {

    @Getter @Setter
    private String auditor;

    @Getter @Setter
    private Date fecha;
}
