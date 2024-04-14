package com.home.bakery.data.dto.request;

import com.home.bakery.data.constans.SortType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaginationRequest {
    private int page;
    private int size;
    private String sortField;
    private SortType sortType;
}
