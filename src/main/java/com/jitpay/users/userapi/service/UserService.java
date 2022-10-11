package com.jitpay.users.userapi.service;

import com.jitpay.users.userapi.dto.*;
import com.jitpay.users.userapi.exception.ApplicationException;
import com.jitpay.users.userapi.mapper.EntityDTOMapper;
import com.jitpay.users.userapi.model.Location;
import com.jitpay.users.userapi.model.User;
import com.jitpay.users.userapi.repository.UserRepository;
import com.jitpay.users.userapi.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserService {

    @Autowired
    private EntityDTOMapper entityDTOMapper;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Object createUserData(CreateUserDataRequestDTO createUserDataRequestDTO) {
        createUserDataRequestDTO.setUserId(CommonUtil.uniqueIdGenerator());
        User user = entityDTOMapper.toUser(createUserDataRequestDTO);
        user = userRepository.save(user);
        return entityDTOMapper.toCreateUserDataRequestDTO(user);
    }

    @Transactional
    public Object createUserLocationData(String userId, LocationDTO locationDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            List<Location> locationList = CollectionUtils.isEmpty(user.getLocations()) ?
                    new ArrayList<>() : user.getLocations();

            Location location = entityDTOMapper.toLocation(locationDTO);
            location.setUser(user);
            locationList.add(location);
            user.setLocations(locationList);
            user = userRepository.save(user);
            return entityDTOMapper.toCreateUserDataRequestDTO(user);
        }else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }

    }

    public Object getLatestUserDetails(String userId) {
        List<CreateUserDataRequestDTO> createUserDataRequestDTOs = userRepository.findTopUserDetails(userId,  PageRequest.of(0, 1));
        if(!CollectionUtils.isEmpty(createUserDataRequestDTOs)) {
            log.info(createUserDataRequestDTOs);
            return createUserDataRequestDTOs;
        }else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }

    public Object getUserLocationDetails(String userId, LocationDetailRequestDTO locationDetailRequestDTO) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
        List<CaptureUserLocationData> captureUserLocationDataList = null;
        try {
            captureUserLocationDataList = userRepository.getUserLocationDetailsInDateRange
                    (userId, formatter.parse(locationDetailRequestDTO.getFromDate()), formatter.parse(locationDetailRequestDTO.getToDate()));
            if(!CollectionUtils.isEmpty(captureUserLocationDataList)) {
                Map<String, List<LocationListDto>> map = captureUserLocationDataList.stream().collect(Collectors.groupingBy(CaptureUserLocationData::getUserId,
                        Collectors.mapping(data -> new LocationListDto(data.getCreatedOn().toString(), data.getLatitude(),
                                data.getLongitude()),Collectors.toList())));
                Map.Entry<String, List<LocationListDto>> entry = map.entrySet().iterator().next();
                return UserLocationListDTO.builder().userId(entry.getKey()).locationListDtoList(entry.getValue()).build();
            }
        } catch (ParseException e) {
            throw new ApplicationException(ErrorDetailDTO.builder().issueCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .errorMessage("please provide date in dd-MM-yyyy hh:mm:ss format").build());
        }
        return null;
    }
}
