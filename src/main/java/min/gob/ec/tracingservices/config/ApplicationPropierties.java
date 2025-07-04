package min.gob.ec.tracingservices.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource("classpath:application.properties")
public class ApplicationPropierties {
    @Value("${directoryfiles}")
    private String directoryfiles;
    @Value("${API_URL_CI}")
    private String API_URL_CI;
    @Value("${API_URL_RUC}")
    private String API_URL_RUC;
    @Value("${AUTH_NAME}")
    private String AUTH_NAME;
    @Value("${AUTH_PWD}")
    private String AUTH_PWD;
}
