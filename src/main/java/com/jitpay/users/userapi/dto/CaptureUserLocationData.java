package com.jitpay.users.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptureUserLocationData {
    private String userId;
    private Date createdOn;
    private String latitude;
    private String longitude;
}
