package min.gob.ec.tracingservices.audit;

import min.gob.ec.tracingservices.model.common.User;
import min.gob.ec.tracingservices.security.UserPrincipal;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity)
    {
        CustomRevisionEntity entity = (CustomRevisionEntity) revisionEntity;

        User user = new User();
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userPrincipal != null && userPrincipal.getUser().getId() > 0) {
            user = userPrincipal.getUser();
        }

        entity.setAuditor(user.getEmail());
        entity.setFecha(new Date());
    }
}
