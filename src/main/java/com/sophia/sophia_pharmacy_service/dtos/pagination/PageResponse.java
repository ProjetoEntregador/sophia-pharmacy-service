package com.sophia.sophia_pharmacy_service.dtos.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;

    private long totalElements;

    private int totalPages;

    private int size;

    private int page;
}
