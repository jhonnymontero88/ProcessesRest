package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.catalogs.Nationality;
//import min.gob.ec.tracingservices.model.catalogs.StatusOrganization; DEPUTACION 2025
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;

@Entity(name = "person")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "person_seq", allocationSize = 1)
public class Person extends AbstractEntity {
    //CEDULA
    @Column(nullable = false)
    private String documentNumber;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    //APELLIDO
    @Column(nullable = false)
    private String lastName;
    //NOMBRE
    @Column(nullable = false)
    private String firstName;
    //Nacionalidad
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nationality_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Nationality nationality;
    
    @Column(nullable = false)
    private Boolean status= true; 

//    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
//    @RestResource(exported = false)
//    @JsonManagedReference
//    private List<Location> locationsList;
//    @Transient
//    private Location location;
}
