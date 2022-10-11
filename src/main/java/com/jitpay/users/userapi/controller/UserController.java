package com.jitpay.users.userapi.controller;

import com.jitpay.users.userapi.dto.CreateUserDataRequestDTO;
import com.jitpay.users.userapi.dto.LocationDTO;
import com.jitpay.users.userapi.dto.LocationDetailRequestDTO;
import com.jitpay.users.userapi.dto.UserLocationListDTO;
import com.jitpay.users.userapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/user")
@Tag(name = "User",description = "User Apis")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Create user data", description = "API used to create user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details created successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserDataRequestDTO.class))})})
    @PostMapping
    public Object createUserDetails(@RequestBody @Valid CreateUserDataRequestDTO createUserDataRequestDTO) {
        return userService.createUserData(createUserDataRequestDTO);
    }

    @Operation(summary = "Update user location", description = "API used to update user location data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details created successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserDataRequestDTO.class))})})
    @PutMapping(value = "/{userId}")
    public Object createUserLocation(@NotBlank @PathVariable("userId") final String userId,
                                     @RequestBody @Valid LocationDTO locationRequestDTO) {
        return userService.createUserLocationData(userId, locationRequestDTO);
    }

    @GetMapping(value = "/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details fetched successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserDataRequestDTO.class))})})
    public Object getLatestUserDetails(@NotBlank @PathVariable("userId") final String userId) {
        return userService.getLatestUserDetails(userId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details created successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserLocationListDTO.class))})})
    @PostMapping(value = "/{userId}/location")
    public Object getUserLocationDetails(@NotBlank @PathVariable("userId") final String userId,
                                         @RequestBody @Valid LocationDetailRequestDTO locationDetailRequestDTO) {
        return userService.getUserLocationDetails(userId, locationDetailRequestDTO);
    }
}
