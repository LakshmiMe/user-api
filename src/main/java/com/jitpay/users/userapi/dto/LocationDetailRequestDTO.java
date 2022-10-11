package com.jitpay.users.userapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDetailRequestDTO {
    @Schema(example = "09-10-2022 12:00:00", description = "From date in the format dd-MM-yyyy hh:mm:ss.")
    @NotBlank
    private String fromDate;

    @Schema(example = "09-11-2022 12:00:00", description = "To date in the format dd-MM-yyyy hh:mm:ss.")
    @NotBlank
    private String toDate;
}
