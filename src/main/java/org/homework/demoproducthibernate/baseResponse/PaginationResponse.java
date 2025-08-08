package org.homework.demoproducthibernate.baseResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

@Data
@Builder
public class PaginationResponse<T> {
    private List<T> items;
    private Pagination pagination;
}
