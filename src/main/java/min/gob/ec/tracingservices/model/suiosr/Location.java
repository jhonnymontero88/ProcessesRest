package min.gob.ec.tracingservices.model.suiosr;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.audit.AbstractEntity;
import min.gob.ec.tracingservices.model.common.Canton;
import min.gob.ec.tracingservices.model.common.Parish;
import min.gob.ec.tracingservices.model.common.Province;
import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.*;

@Entity(name = "location")
@Getter @Setter
@SequenceGenerator(name = "generic_sequence", sequenceName = "location_seq", allocationSize = 1)
public class Location extends AbstractEntity  {

    @Column(nullable = false)
    private String address;
    @RestResource(exported = false)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id", referencedColumnName = "id", nullable = false)
    private Province province;
    @RestResource(exported = false)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "canton_id", referencedColumnName = "id", nullable = false)
    private Canton canton;
    @RestResource(exported = false)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parish_id", referencedColumnName = "id", nullable = false)
    private Parish parish;
 //FILIAL
 @ManyToOne
 @JoinColumn(name = "filial_id", referencedColumnName = "id", nullable = true)
 @RestResource(exported = false)
 private Filial filial;

}
