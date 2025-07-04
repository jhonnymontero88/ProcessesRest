package min.gob.ec.tracingservices.util;

import min.gob.ec.tracingservices.config.ApplicationPropierties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class RestData {

    private final ApplicationPropierties applicationPropierties;

    @Autowired
    public RestData(ApplicationPropierties applicationPropierties) {
        this.applicationPropierties = applicationPropierties;
    }

    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = applicationPropierties.getAUTH_NAME() + ":" + applicationPropierties.getAUTH_PWD();
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        headers.set("Authorization", "Basic " + new String(encodedAuth));
        return headers;
    }

    public String getApiUrlCi() {
        return applicationPropierties.getAPI_URL_CI();
    }

    public String getApiUrlRuc() {
        return applicationPropierties.getAPI_URL_RUC();
    }
    public RestTemplate getRestTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        return new RestTemplate(requestFactory);
    }

}
