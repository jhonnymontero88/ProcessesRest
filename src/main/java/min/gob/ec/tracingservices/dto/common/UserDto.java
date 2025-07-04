package min.gob.ec.tracingservices.dto.common;

import lombok.Getter;
import lombok.Setter;
import min.gob.ec.tracingservices.model.common.Role;
import min.gob.ec.tracingservices.model.common.Institution;

@Getter @Setter
public class UserDto {
    private Integer id;
    private String email;
    private Boolean active;
    private Role role;
    private Institution institution;
    private String name;

    @Override
    public String toString() {
        return this.email;
    }
}
