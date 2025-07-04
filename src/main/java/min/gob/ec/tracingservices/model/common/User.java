package min.gob.ec.tracingservices.model.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;
// clave pablo.noboa@ministeriodegobierno.gob.ec F9E568EBB387C0A235B2164838B5F68D
@Entity(name = "userd")
@Getter @Setter
public class User {
    @Id
    private Integer id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Person person;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    @RestResource(exported = false)
    private Institution institution;
    @Column(nullable = false)
    private String name;

    @Override
    public String toString() {
        return this.email;
    }
}
