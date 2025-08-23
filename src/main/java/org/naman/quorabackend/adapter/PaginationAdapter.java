package org.naman.quorabackend.adapter;

import org.naman.quorabackend.dto.PaginationRespnseDTO;

import java.util.List;

public class PaginationAdapter {
    public static <T> PaginationRespnseDTO<T> toPaginationResponse(List<T> data, long totalElements, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return  PaginationRespnseDTO.<T>builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .currentPage(page)
                .pageSize(size)
                .data(data)
                .build();
    }
}
