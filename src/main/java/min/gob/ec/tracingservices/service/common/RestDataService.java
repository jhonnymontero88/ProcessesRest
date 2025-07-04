package min.gob.ec.tracingservices.service.common;


import min.gob.ec.tracingservices.util.RestData;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;



@Service
public class RestDataService {

    @Autowired
    private RestData restData;

    public Map<String, Object> getCI(String cedula) {
        RestTemplate restTemplate = restData.getRestTemplate();
        HttpHeaders headers = restData.createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = restData.getApiUrlCi()+ cedula;
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getRuc(String ruc) {
        RestTemplate restTemplate = restData.getRestTemplate();
        HttpHeaders headers = restData.createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = restData.getApiUrlRuc()+ruc;
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return response.getBody();
    }


}