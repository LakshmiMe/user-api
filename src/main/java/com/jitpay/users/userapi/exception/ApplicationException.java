package com.jitpay.users.userapi.exception;

import com.jitpay.users.userapi.dto.ErrorDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationException extends RuntimeException {
    private ErrorDetailDTO errorDetailDTO;
}
