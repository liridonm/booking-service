package com.liridonmorina.bookingservice.domain.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper {
    private boolean success;
    private String message;
    private Integer status;
    private Object data;


    public ResponseWrapper(String message, Object data) {
        this.message = message;
        this.data = data;
        this.status = HttpStatus.OK.value();
        this.success = true;
    }

    public ResponseWrapper(String message) {
        this.message = message;
        this.status = HttpStatus.OK.value();
        this.success = true;
    }
}