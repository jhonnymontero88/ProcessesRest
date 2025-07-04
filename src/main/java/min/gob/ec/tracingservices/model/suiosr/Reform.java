package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.catalogs.TypeReform;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "reform")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "reform_seq", allocationSize = 1)
public class Reform extends AbstractEntity {
    //ORGANIZACION
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Organization organization;

      //Tipo Reforma
      @ManyToOne
      @JoinColumn(name = "typereform_id", referencedColumnName = "id", nullable = false)
      @RestResource(exported = false)
      private TypeReform typereform;

    //NUM DE ACUERDO MINISTERIAL DE REFORMA
    @Column(nullable = false)
    private String documentregister_num;
    //FECHA DE ACUERDO MINISTERIAL DE LA REFORMA
    @Column(nullable = false)
    private Date documentregister_date;
    //FECHA DE INSCRIPCION REGISTRO DE LA PROPIEDAD DEL ACUERDO MINISTERIAL
    @Column(nullable = true)
    private Date notificationregister_date;
    //TIPO DE REFORMA (GENERAL O FILIAL)
    private boolean type_bool_reform;
    @Column(nullable = true)
    private String observation;

}
