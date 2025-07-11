package min.gob.ec.tracingservices;

import min.gob.ec.tracingservices.model.catalogs.*;
import min.gob.ec.tracingservices.model.common.*;
import min.gob.ec.tracingservices.model.suiosr.*;
import min.gob.ec.tracingservices.model.notifications.Notification;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class TracingRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracingRestApplication.class, args);
    }

    @PostConstruct
    public void init(){
        // TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(){
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Notification.class);
            config.exposeIdsFor(Role.class);
            
            config.exposeIdsFor(User.class);
            config.exposeIdsFor(Files.class);
            //
            
            config.exposeIdsFor(Person.class);
            config.exposeIdsFor(Location.class);
            
            config.exposeIdsFor(CurrentReligious.class);
            config.exposeIdsFor(StatusOrganization.class);
            config.exposeIdsFor(TypeOrganization.class);
            config.exposeIdsFor(TypeReform.class);
            config.exposeIdsFor(TypeProcedure.class);
            config.exposeIdsFor(Institution.class);
            config.exposeIdsFor(Expedient.class);
            config.exposeIdsFor(Filial.class);
            config.exposeIdsFor(Location.class);
            config.exposeIdsFor(Organization.class);
            config.exposeIdsFor(Reform.class);
            config.exposeIdsFor(Nationality.class);
            
            config.exposeIdsFor(Contact.class);
            
        });
    }
}
