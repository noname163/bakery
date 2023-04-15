package com.home.bakery.services.address.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.home.bakery.data.repositories.CityRepository;
import com.home.bakery.exceptions.BadRequestException;
import com.home.bakery.exceptions.message.Message;

import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CityServiceImplTest {
    private CityRepository cityRepository;
    private Message message;
    private CityServiceImpl cityServiceImpl;
    @BeforeEach
    void setUp(){
        cityRepository = mock(CityRepository.class);
        message = mock(Message.class);
        cityServiceImpl =  cityServiceImpl.builder().cityRepository(cityRepository).message(message).build();
    }
    @Test
    void testAddCity() {
        when(cityRepository.existsByName("test")).thenReturn(true);
        when(message.objectExistMessage("City", "test")).thenReturn("City name test already exist.");
        BadRequestException actual = Assertions.assertThrows(BadRequestException.class,()-> cityServiceImpl.addCity("test"));

        assertThat(actual.getMessage(), is("City name test already exist."));
    }
}
