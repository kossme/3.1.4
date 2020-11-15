import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

public class Test {

    static String JSESSIONID;
    static String finalCode;
    static final String URL_USERS = "http://91.241.64.178:7081/api/users";


    /*DELETE USER*/
    public static void deleteUser() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, JSESSIONID);
        HttpEntity<?> request = new HttpEntity<Object>(headers);

        ResponseEntity<String> response  = restTemplate.exchange(URL_USERS + "/3", HttpMethod.DELETE, request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    /*PUT USER*/
    public static void putUser() {

        User editUser = new User(3L, "Thomas", "Shelby", (byte) 33);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, JSESSIONID);
        //headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<User> requestEntity = new HttpEntity<User>(editUser, headers);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> entity = new HttpEntity<User>(editUser, headers);
        System.out.println(editUser);

        System.out.println(headers);
        ResponseEntity<String> response = restTemplate.exchange (URL_USERS, HttpMethod.PUT, entity, String.class);

        System.out.println(response.getStatusCode());
        response.getStatusCode();
        String responseBody = response.getBody();
    }

    /*POST USER*/
    public static void postUser() {
        User newUser = new User(3L, "James", "Brown", (byte) 33);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, JSESSIONID);
        headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<User> requestBody = new HttpEntity<>(newUser, headers);

        //ResponseEntity<String> result = restTemplate.postForEntity(URL_CREATE_EMPLOYEE, requestBody, String.class);
        ResponseEntity<String> result = restTemplate.exchange(URL_USERS, HttpMethod.POST, requestBody, String.class);
        System.out.println(result.getBody());         //5ebfeb
        finalCode = (String) result.getBody();
        System.out.println("Status code:" + result.getStatusCode());
    }


    /*GET ALL USERS*/

    public static void getAllUsers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<User[]> response = restTemplate.getForEntity(URL_USERS, User[].class);
        User[] users = response.getBody();

        HttpHeaders headers = response.getHeaders();

        System.out.println(Arrays.toString(users));
        System.out.println(response.getHeaders());
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        System.out.println(set_cookie);
        if (users != null) {
            for (User e : users) {
                System.out.println("User: " + e.getId() + " - " + e.getName() + " - " + e.getLastName() + " - " + e.getAge());
            }
        }
        if (set_cookie != null) {
            //JSESSIONID = set_cookie.substring(0, set_cookie.indexOf(';'));
            JSESSIONID = set_cookie;
            System.out.println(JSESSIONID);
        }
    }

}
