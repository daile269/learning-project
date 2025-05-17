package com.learning.user_service.mockServer;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.learning.user_service.UserServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;


@SpringBootTest(classes = UserServiceApplication.class)
@WireMockTest(httpPort = 8089)
public class UserServiceMockTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGetUserById() {
        stubFor(get(urlEqualTo("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"username\":\"admin\", \"password\":\"admin\", \"email\": \"admin@gmail.com\" }")));


        String response = restTemplate.getForObject("http://localhost:8089/api/users/1", String.class);

        assertEquals("{ \"username\":\"admin\", \"password\":\"admin\", \"email\": \"admin@gmail.com\" }", response);
    }

    @Test
    public void testCreateUser() {

        String requestBody = "{ \"username\":\"admin\", \"password\":\"admin\", \"email\": \"admin@gmail.com\" }";

        stubFor(post(urlEqualTo("/api/users"))
                .withRequestBody(equalToJson(requestBody))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"username\":\"admin\", \"password\":\"admin\", \"email\": \"admin@gmail.com\" }")));


        String url = "http://localhost:8089/api/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("{ \"username\":\"admin\", \"password\":\"admin\", \"email\": \"admin@gmail.com\" }", response.getBody());
    }

    @Test
    public void testServerErrorResponse() {
        stubFor(get(urlEqualTo("/api/servererror"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("{ \"error\": \"Internal Server Error\" }")));

        try {
            restTemplate.getForObject("http://localhost:8089/api/servererror", String.class);
            fail("Should have thrown HttpServerErrorException");
        } catch (HttpServerErrorException e) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
        }
    }


}
