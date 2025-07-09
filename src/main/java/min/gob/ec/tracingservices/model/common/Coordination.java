package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "coordination") // Crea la entidad en a base de datos
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "coordination_seq", allocationSize = 1) // Genera el valor de id de la llave primaria de la entidad
public class Coordination extends AbstractEntity{ // Hereda atributos de la clase AbstractEntity
    @Column(nullable = false) // Valor de la columna no puede ser nula
    private String area; // Atributo de la tabla ManualProcesses
    @Column(nullable = false)
    private String coordsubse;

    @ManyToOne // Muchos a uno relacion de tablas 
    @JoinColumn(name = "statessectiondocumentsid", referencedColumnName = "id", nullable = false ) // Union de tablas Coordination con ManualProcesses
    @RestResource(exported = false) 
    private StatesSectionDocuments statessectiondocuments; // Todo el contenido del objeto relacionado
}