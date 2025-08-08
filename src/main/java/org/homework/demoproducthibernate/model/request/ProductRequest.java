package org.homework.demoproducthibernate.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

public record ProductRequest(
        @NotBlank(message = "product name can not be blank")
        @Size(max = 250, message = "Product name must be at most 250 characters")
        @Pattern(
                regexp = ".*[a-zA-Z0-9].*",
                message = "Product name must contain at least one alphanumeric character"

        )
        @Schema(defaultValue = "productName")
        String productName,
        @NotNull(message = "price can not be null")
        @Positive(message = "price must be positive")
        @Schema(defaultValue = "0.0")
        Double price,
        @NotNull(message = "quantity can not be null")
        @Min(value = 1, message = "quantity must be at least 1")
        @Schema(defaultValue = "1")
        Integer quantity
) {
}