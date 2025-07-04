package min.gob.ec.tracingservices.dto.suiosr;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthOnLineDTO {
    private String token;
    private String user;
    private String pwd;
    private String img;
    private String ip;
}
