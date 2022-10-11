package com.jitpay.users.userapi.mapper;

import com.jitpay.users.userapi.dto.CreateUserDataRequestDTO;
import com.jitpay.users.userapi.dto.LocationDTO;
import com.jitpay.users.userapi.model.Location;
import com.jitpay.users.userapi.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EntityDTOMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toUser(CreateUserDataRequestDTO createUserDataRequestDTO);

    Location toLocation(LocationDTO locationDTO);

    CreateUserDataRequestDTO toCreateUserDataRequestDTO(User user);
}
