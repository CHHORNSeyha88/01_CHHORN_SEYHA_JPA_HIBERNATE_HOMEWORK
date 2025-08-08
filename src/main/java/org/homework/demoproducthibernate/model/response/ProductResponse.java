package org.homework.demoproducthibernate.model.response;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

public record ProductResponse(
        Long id,
        String productName,
        Double price,
        Integer quantity
) {
}
