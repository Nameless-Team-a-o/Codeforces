package com.namelessCodeF.codeforces.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.namelessCodeF.codeforces.dto.Judge0SubmissionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Judge0Client {

    private final RestTemplate restTemplate;

    @Value("${judge0.url}")
    private  String judge0Url;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final Logger log = LoggerFactory.getLogger(Judge0Client.class);

    public void sendSubmission(Judge0SubmissionDTO judge0SubmissionDTO) {
        try {
            String jsonBody = objectMapper.writeValueAsString(judge0SubmissionDTO);
            ResponseEntity<String> response = sendPostRequest(jsonBody);
            logResponse(jsonBody, response);
        } catch (RestClientException e) {
            handleRestClientException(e);
        } catch (Exception e) {
            handleGenericException(e);
        }
    }

    private ResponseEntity<String> sendPostRequest(String jsonBody) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        return restTemplate.exchange(judge0Url, HttpMethod.POST, requestEntity, String.class);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer sk_live_B8HH5gc2meJ9Rv2vuoYQ3pqUsKWAGHX8");
        return headers;
    }

    private void logResponse(String jsonBody, ResponseEntity<String> response) {
        log.info("JSON Body: {}", jsonBody);
        log.info("Response received: {}", response.getBody());
    }

    private void handleRestClientException(RestClientException e) {
        log.error("RestClientException occurred: {}", e.getMessage());
    }

    private void handleGenericException(Exception e) {
        log.error("An error occurred: {}", e.getMessage());
    }
}
