package com.example.ist_library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponse {

    Integer page;
    Integer size;
    Integer totalPage;
    Long totalData;

}
