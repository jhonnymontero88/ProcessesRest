package min.gob.ec.tracingservices.util;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Random;

public class RestClientEmail {
    private final static String API_URL = "http://localhost:4999/".concat("email/send");
    private final static String AUTH_NAME = "G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A";
    private final static String AUTH_PWD = "@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*";
    private final static int AUTH_LEN = 7;

    private static RestTemplate getRestTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        return new RestTemplate(requestFactory);
    }

    private static HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String auth = new String(Base64.getEncoder().encode((getRnd() + AUTH_NAME + getRnd()).getBytes()))
                        + ":" +
                        new String(Base64.getEncoder().encode((getRnd() + AUTH_PWD + getRnd()).getBytes()));
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    private static String getRnd(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(AUTH_LEN)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String post(String data){
        String result = "";
        try{
            RestTemplate restTemplate = getRestTemplate();
            HttpEntity<String> request = new HttpEntity<String>(data, createHeaders());
            result = restTemplate.postForObject(API_URL, request, String.class);
        }catch (Exception ex){
            result = "Error al procesar los datos en el servicio para envío de correos: " + ex.getMessage();
        }

        return result;
    }
}
