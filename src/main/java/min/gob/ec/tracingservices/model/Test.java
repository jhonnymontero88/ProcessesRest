package min.gob.ec.tracingservices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "test")
public class Test {
    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
