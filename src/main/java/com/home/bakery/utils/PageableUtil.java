package com.home.bakery.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.home.bakery.exceptions.BadRequestException;

@Component
public class PageableUtil {
    public Pageable getPageable(int offset, int limit, String fieldName, String sortType) throws BadRequestException {
        if(sortType == null || sortType.isEmpty()){
            sortType = "ASC";
        }
        if(fieldName== null|| fieldName.isEmpty()){
            fieldName = "";
        }
        if (sortType.trim().equalsIgnoreCase("ASC")) {
            return PageRequest.of(offset, limit, Sort.by(fieldName).ascending());
        }
        if (sortType.trim().equalsIgnoreCase("DESC")) {
            return PageRequest.of(offset, limit, Sort.by(fieldName).descending());
        }
        throw new BadRequestException("Invalid sort type");
    }
}

