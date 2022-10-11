package com.jitpay.users.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLocationListDTO {
    private String userId;
    private List<LocationListDto> locationListDtoList;
}
