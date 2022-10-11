package com.jitpay.users.userapi.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jitpay.users.userapi.UserApiApplication;
import com.jitpay.users.userapi.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = UserApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateUser() {
        String url = "http://localhost:" + port + "/user";
        CreateUserDataRequestDTO createUserDataRequestDTO = addCreateUserDataRequestDTO();
        HttpEntity<?> entity = new HttpEntity<>(createUserDataRequestDTO);

        ResponseEntity<CreateUserDataRequestDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                CreateUserDataRequestDTO.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody().getUserId());
    }

    @Test
    public void testCreateUserLocation() {
        String url = "http://localhost:" + port + "/user/ba571aae-51a3-40d5-816e-e27f74c75997";
        LocationDTO dto = new LocationDTO("76.3456","98.2345678");
        HttpEntity<?> entity = new HttpEntity<>(dto);

        ResponseEntity<CreateUserDataRequestDTO> response = restTemplate.exchange(url, HttpMethod.PUT, entity,
                CreateUserDataRequestDTO.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateUserLocationWhenUserNotPresent() {
        String url = "http://localhost:" + port + "/user/userId123";
        LocationDTO dto = new LocationDTO("76.3456","98.2345678");
        HttpEntity<?> entity = new HttpEntity<>(dto);

        ResponseEntity<ErrorDetailDTO> response = restTemplate.exchange(url, HttpMethod.PUT, entity,
                ErrorDetailDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getErrorMessage(),"User not found");
    }

    @Test
    public void testGetLatestUserDetailsWhenUserNotPresent() {
        String url = "http://localhost:" + port + "/user/userId123";
        ResponseEntity<ErrorDetailDTO> response = restTemplate.exchange(url, HttpMethod.GET, null,
                ErrorDetailDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getErrorMessage(),"User not found");
    }

    @Test
    public void testGetLatestUserDetails() {
        String url = "http://localhost:" + port + "/user/ba571aae-51a3-40d5-816e-e27f74c75997";
        ResponseEntity<List<CreateUserDataRequestDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                (Class<List<CreateUserDataRequestDTO>>)(Object)List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserLocationDetails() {
        String url = "http://localhost:" + port + "/user/ba571aae-51a3-40d5-816e-e27f74c75997/location";
        LocationDetailRequestDTO locationDetailRequestDTO = new LocationDetailRequestDTO("09-10-2022 23:30:00","30-10-2022 23:30:00");
        HttpEntity<?> entity = new HttpEntity<>(locationDetailRequestDTO);

        ResponseEntity<UserLocationListDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                UserLocationListDTO.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    private CreateUserDataRequestDTO addCreateUserDataRequestDTO() {
        CreateUserDataRequestDTO dto = new CreateUserDataRequestDTO();
        dto.setUserId("userId");
        dto.setEmail("test@gmail.com");
        dto.setFirstName("FirstName");
        dto.setLastName("LastName");
        return dto;
    }

}
