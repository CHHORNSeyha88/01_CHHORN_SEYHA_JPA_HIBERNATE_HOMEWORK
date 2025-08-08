package org.homework.demoproducthibernate.baseResponse;

import lombok.Builder;
import lombok.Data;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

@Data
@Builder
public class Pagination {
    private Long totalElements;
    private int currentPages;
    private int pageSize;
    private int totalPages;
}
