package min.gob.ec.tracingservices.repository.notifications;

import min.gob.ec.tracingservices.model.notifications.Notification;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "notification", collectionResourceRel = "balder")
public interface NotificationRepository extends ListCrudRepository<Notification, Integer> {
    List<Notification> findByDoneTrueOrderByCreationDateDesc();
    List<Notification> findByDoneFalseOrderByCreationDate();
}
