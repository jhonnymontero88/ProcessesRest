package min.gob.ec.tracingservices.model.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVerificationResponse {
    private String user;
    private String jwt;
    private boolean authValid;
    private boolean tokenValid;
    private boolean error;
    private String message;
}
