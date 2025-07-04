package min.gob.ec.tracingservices.service.common;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;


@Service
public class CommonService {

    @PersistenceContext
    private EntityManager em;

    public Date getDate(){
        Query query = em.createNativeQuery("SELECT NOW()");
        Date dateItem = (Date) query.getSingleResult();
        return dateItem;
    }
}
