package com.jitpay.users.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationListDto {
    private String createdOn;
    private String latitude;
    private String longitude;
}
