package org.homework.demoproducthibernate.baseResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.Instant;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

@Setter
@Getter
@Builder
public class ApiResponse <T>{
    private String message;
    private T payload;
    private Instant instant;
}
