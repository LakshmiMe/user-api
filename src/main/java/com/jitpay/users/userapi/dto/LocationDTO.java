package com.jitpay.users.userapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    @Schema(example = "98.623572681", description = "Latitude of user location", required = true )
    @NotBlank
    private String latitude;

    @Schema(example = "12.45973868", description = "Longitude of user location", required = true )
    @NotBlank
    private String longitude;
}
