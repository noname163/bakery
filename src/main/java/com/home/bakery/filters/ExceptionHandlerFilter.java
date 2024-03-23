package com.home.bakery.filters;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.bakery.data.dto.response.ExceptionResponse;
import com.home.bakery.exceptions.APIExceptionHandler;
import com.home.bakery.exceptions.ForbiddenException;
import com.home.bakery.exceptions.NotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            if (ex instanceof ExpiredJwtException) {
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
            else if(ex instanceof NotFoundException){
                httpStatus = HttpStatus.NOT_FOUND;
            }
            else if (ex instanceof ForbiddenException) {
                httpStatus = HttpStatus.FORBIDDEN;
            }
            setErrorResponse(httpStatus, response, ex);
            log.debug("ExceptionHandlerFilter called");
            
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(ex.getMessage()).build();
        try {
            String json = convertObjectToJson(exceptionResponse.getMessage());
            response.getWriter().write(json);
            log.debug("Setting error response with status " + status.value() + " and message " + exceptionResponse.getMessage());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
