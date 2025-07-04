package min.gob.ec.tracingservices.audit;

import min.gob.ec.tracingservices.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;

@Component
public class AbstractEntityListener {

    @PrePersist
    public void dateCreate(AbstractEntity a){
        UserPrincipal userPrincipal = null;
        try{
            userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            a.setCreationDate(new Date());
            a.setCreationUser(userPrincipal.getUser().getEmail());
        }catch (Exception ex){
        }
    }

    @PreUpdate
    public void dateUpdate(AbstractEntity a){
    }
}
