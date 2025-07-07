package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.*;

@Entity(name = "coordination")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "coordination", allocationSize = 1)
public class Coordination {
    @Column(nullable = false) // valor de la columna no puede ser nula
    private String area; // atributo de la tabla ManualProcesses
    @Column(nullable = false)
    private String coordSubse;

    @ManyToOne // muchos a uno relacion de tablas 
    @JoinColumn(name = "statesSectionDocumentsID", referencedColumnName = "id", nullable = false ) // union de tablas Coordination con ManualProcesses
    @RestResource(exported = false)
    private StatesSectionDocuments statesSectionDocuments; // todo el contenido del objeto relacionado
}