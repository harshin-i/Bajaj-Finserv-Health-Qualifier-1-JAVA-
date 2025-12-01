package com.bajaj;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GENERATE_URL =
            "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    // Your details
    private static final String NAME = "Harshini V";
    private static final String REGNO = "22BCE20284";
    private static final String EMAIL = "your-email@example.com";

    // FINAL SQL QUERY (Question 2)
    private static final String FINAL_SQL =
            "SELECT d.DEPARTMENT_NAME,\n" +
            "       ROUND(AVG(TIMESTAMPDIFF(YEAR, e.DOB, CURDATE())), 2) AS AVERAGE_AGE,\n" +
            "       (\n" +
            "           SELECT GROUP_CONCAT(full_name SEPARATOR ', ')\n" +
            "           FROM (\n" +
            "               SELECT CONCAT(e2.FIRST_NAME, ' ', e2.LAST_NAME) AS full_name\n" +
            "               FROM EMPLOYEE e2 \n" +
            "               JOIN PAYMENTS p2 ON p2.EMP_ID = e2.EMP_ID\n" +
            "               WHERE e2.DEPARTMENT = d.DEPARTMENT_ID\n" +
            "                 AND p2.AMOUNT > 70000\n" +
            "               ORDER BY e2.FIRST_NAME, e2.LAST_NAME\n" +
            "               LIMIT 10\n" +
            "           ) AS t\n" +
            "       ) AS EMPLOYEE_LIST\n" +
            "FROM DEPARTMENT d\n" +
            "JOIN EMPLOYEE e ON e.DEPARTMENT = d.DEPARTMENT_ID\n" +
            "JOIN PAYMENTS p ON p.EMP_ID = e.EMP_ID\n" +
            "WHERE p.AMOUNT > 70000\n" +
            "GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME\n" +
            "ORDER BY d.DEPARTMENT_ID DESC;";

    public void execute() {

        // STEP 1: Generate webhook
        Map<String, Object> req = Map.of(
                "name", NAME,
                "regNo", REGNO,
                "email", EMAIL
        );

        ResponseEntity<Map> response =
                restTemplate.postForEntity(GENERATE_URL, req, Map.class);

        String webhook = (String) response.getBody().get("webhook");
        String token = (String) response.getBody().get("accessToken");

        System.out.println("Webhook URL = " + webhook);
        System.out.println("Access Token = " + token);

        // STEP 2: Prepare request to webhook
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        Map<String, String> body = Map.of("finalQuery", FINAL_SQL);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // STEP 3: Submit final query
        ResponseEntity<String> submitResponse =
                restTemplate.postForEntity(webhook, entity, String.class);

        System.out.println("Submission Response: " + submitResponse.getBody());
    }
}
