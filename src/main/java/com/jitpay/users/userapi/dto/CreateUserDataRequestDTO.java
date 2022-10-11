package com.jitpay.users.userapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserDataRequestDTO {

    @Schema(example = "userid-123", description = "User id of the user." )
    private String userId;

    private String createdOn;

    @Schema(example = "test123@gmail.com", description = "Email id.")
    @Email
    private String email;

    @Schema(example = "John", description = "First name of the user.")
    private String firstName;

    @Schema(example = "Doe", description = "Last name of the user.")
    private String lastName;

    private LocationDTO locationDTO;

    public CreateUserDataRequestDTO(String userId, Date createdOn, String email, String firstName, String lastName,
                                    String latitude, String longitude) {
        this.userId = userId;
        this.createdOn = createdOn.toString();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locationDTO = new LocationDTO(latitude, longitude);
    }
}
