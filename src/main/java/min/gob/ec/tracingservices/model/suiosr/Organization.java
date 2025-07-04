package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.catalogs.CurrentReligious;
import min.gob.ec.tracingservices.model.common.Institution;
import min.gob.ec.tracingservices.model.catalogs.StatusOrganization;
import min.gob.ec.tracingservices.model.catalogs.TypeOrganization;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "organization")
@Getter
@Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "organization_seq", allocationSize = 1)
public class Organization extends AbstractEntity {
    //RUC
    @Column
    private String ruc;
    //INSTITUCION
    @OneToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Institution institution;
    //NOMBRE DE ORG
    @Column(nullable = false)
    private String name;
    //EMAIL
    @Column(nullable = false)
    private String email;
    //TELF
    @Column(nullable = false)
    private String phone;
    //ESTADO DE ORGANIZACION (CATALOGO)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusOrganization_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private StatusOrganization statusOrganization;
    //TIPO DE ORGANIZACION (CATALOGO)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeOrganization_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private TypeOrganization typeOrganization;
    //CORRIENTE RELIGIOSA (CATALOGO)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currentreligious_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private CurrentReligious currentReligious;
    //NUM ACUERDO MINISTERIAL
    @Column(nullable = true)
    private String numAgreementMinisterial;
    //FECHA APROBACION ACUERDO MIN
    @Column(nullable = true)
    private Date settlementApprovalDate;
    //FECHA DE REGISTRO EN REGISTRO DE PROPIEDAD
    @Column(nullable = true)
    private Date dataRegistrationPropertyAgreement;
//    Num de miembros legal
//    @Column(nullable = true)
//    private int numMembersLegal;
    //ESTADO DE CREACION DE ORGANIZACION INTERNO
    private String internalState;
    //CAMPO PARA SABER SI EL REGISTRO SE COMPLETO
    @Column(nullable = true)
    private Boolean registrationCompleted;


}
