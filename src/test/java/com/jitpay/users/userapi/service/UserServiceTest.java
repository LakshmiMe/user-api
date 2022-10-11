package com.jitpay.users.userapi.service;

import com.jitpay.users.userapi.dto.*;
import com.jitpay.users.userapi.exception.ApplicationException;
import com.jitpay.users.userapi.mapper.EntityDTOMapper;
import com.jitpay.users.userapi.model.Location;
import com.jitpay.users.userapi.model.User;
import com.jitpay.users.userapi.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private EntityDTOMapper entityDTOMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateUserData() {
        when(entityDTOMapper.toUser(any())).thenReturn(mockUser());
        when(userRepository.save(any())).thenReturn(mockUser());
        when(entityDTOMapper.toCreateUserDataRequestDTO(any())).thenReturn(mockUserRequestData());
        CreateUserDataRequestDTO actual = (CreateUserDataRequestDTO) userService.createUserData(mockUserRequestData());
        assertEquals(actual.getUserId(),"userId");
        assertEquals(actual.getFirstName(),"FirstName");
    }

    @Test
    public void testCreateUserLocationData() {
        when(userRepository.findById(any())).thenReturn(Optional.of(mockUser()));
        when(entityDTOMapper.toLocation(any())).thenReturn(mockLocation());
        when(userRepository.save(any())).thenReturn(mockUserWithLocation());
        when(entityDTOMapper.toCreateUserDataRequestDTO(any())).thenReturn(mockUserRequestDataWithLocation());
        CreateUserDataRequestDTO actual = (CreateUserDataRequestDTO) userService.createUserLocationData("userId",mockLocationDto());
        assertEquals(actual.getUserId(),"userId");
        assertEquals(actual.getFirstName(),"FirstName");
        assertEquals("98.3456789",actual.getLocationDTO().getLatitude());
    }

    @Test
    public void testCreateUserLocationDataWhenUserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.createUserLocationData("userId",mockLocationDto());
        });
        assertEquals(exception.getReason(),"User not found");
    }

    @Test
    public void testGetLatestUserDetails() {
        when(userRepository.findTopUserDetails(any(),any())).thenReturn(mockUserDataWithRecentLocation());
        List<CreateUserDataRequestDTO> actual = (List<CreateUserDataRequestDTO>) userService.getLatestUserDetails("userId");
        assertEquals(actual.get(0).getUserId(),"userId");
        assertEquals(actual.get(0).getFirstName(),"FirstName");
        assertEquals("98.3456789",actual.get(0).getLocationDTO().getLatitude());
    }

    @Test
    public void testGetUserLocationDetails() {
        when(userRepository.getUserLocationDetailsInDateRange(any(),any(),any())).thenReturn(mockCaptureUserLocationData());
        UserLocationListDTO actual = (UserLocationListDTO) userService.getUserLocationDetails("userId", mockLocationDetailRequestDTO());
        assertEquals(actual.getUserId(),"userId");
        assertEquals(actual.getLocationListDtoList().size(),2);
        assertEquals("98.345678",actual.getLocationListDtoList().get(0).getLatitude());
    }

    @Test
    public void testGetUserLocationDetailsWhenDateParsingErrorOccurs() {
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            userService.getUserLocationDetails("userId", new LocationDetailRequestDTO("2002-10-10 12:00:00", "09-11-2022"));
        });
        assertEquals(exception.getErrorDetailDTO().getErrorMessage(),"please provide date in dd-MM-yyyy hh:mm:ss format");
    }
    private LocationDetailRequestDTO mockLocationDetailRequestDTO() {
        return new LocationDetailRequestDTO("09-10-2022 12:00:00", "09-11-2022 12:00:00");
    }

    private List<CaptureUserLocationData> mockCaptureUserLocationData() {
        List<CaptureUserLocationData> dtos = new ArrayList<>();
        CaptureUserLocationData data = new CaptureUserLocationData();
        data.setUserId("userId");
        data.setLatitude("98.345678");
        data.setLongitude("76.98765");
        data.setCreatedOn(new Date());
        dtos.add(data);
        CaptureUserLocationData data2 = new CaptureUserLocationData();
        data2.setUserId("userId");
        data2.setLatitude("98.345678");
        data2.setLongitude("76.98765");
        data2.setCreatedOn(new Date());
        dtos.add(data2);
        return dtos;
    }

    private List<CreateUserDataRequestDTO> mockUserDataWithRecentLocation() {
        CreateUserDataRequestDTO dto = new CreateUserDataRequestDTO();
        dto.setUserId("userId");
        dto.setEmail("test@gmail.com");
        dto.setFirstName("FirstName");
        dto.setLastName("LastName");
        dto.setLocationDTO(mockLocationDto());
        return Arrays.asList(dto);
    }

    private Object mockUserWithLocation() {
        User user = mockUser();
        user.setLocations(Arrays.asList(mockLocation()));
        return user;
    }

    private LocationDTO mockLocationDto() {
        return new LocationDTO("98.3456789", "34.3456278");
    }

    private Location mockLocation() {
        Location location = new Location();
        location.setUser(mockUser());
        location.setLatitude("98.3456789");
        location.setLatitude("34.3456278");
        location.setCreatedOn(new Date());
        return location;
    }

    private CreateUserDataRequestDTO mockUserRequestData() {
        CreateUserDataRequestDTO dto = new CreateUserDataRequestDTO();
        dto.setUserId("userId");
        dto.setEmail("test@gmail.com");
        dto.setFirstName("FirstName");
        dto.setLastName("LastName");
        return dto;
    }

    private CreateUserDataRequestDTO mockUserRequestDataWithLocation() {
        CreateUserDataRequestDTO dto = mockUserRequestData();
        dto.setLocationDTO(mockLocationDto());
        return dto;
    }

    private User mockUser() {
        User user = new User();
        user.setUserId("userId");
        user.setEmail("test@gmail.com");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        return user;
    }
}
