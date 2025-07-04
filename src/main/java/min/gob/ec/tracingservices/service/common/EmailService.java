package min.gob.ec.tracingservices.service.common;

import min.gob.ec.tracingservices.dto.common.EmailTransactionDto;
import min.gob.ec.tracingservices.util.RestClientEmail;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EmailService{
    public void sendMail(String email, String subject, String message){
        EmailTransactionDto dto = new EmailTransactionDto();
        dto.setInstitution("");
        dto.setSystem("");
        dto.setFrom("notificaciones@ministeriodegobierno.gob.ec");
        dto.setTo(email);
        dto.setSubject(subject);
        dto.setMessage(Base64.getEncoder().encodeToString(message.getBytes()));
        dto.setIncludeTemplate(false);

        Gson g = new Gson();
        RestClientEmail.post(g.toJson(dto));
    }
}
