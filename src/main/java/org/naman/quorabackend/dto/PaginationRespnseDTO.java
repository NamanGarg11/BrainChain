package org.naman.quorabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaginationRespnseDTO<T> {

        private long totalElements;
        private int totalPages;
        private int currentPage;
        private int pageSize;
        private List<T> data;


}
