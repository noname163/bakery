package com.home.bakery.mappers;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MapperTemplate<ENTITY, REQUEST, RESPONSE, INTERFACEOBJECT> {
    public abstract ENTITY mapRequestToEntity(REQUEST object);

    public List<ENTITY> mapRequestsToEntities(List<REQUEST> objects) {
        return objects.stream().map(this::mapRequestToEntity).collect(Collectors.toList());
    }

    public abstract RESPONSE mapEntityToResponse(ENTITY object);

    public List<RESPONSE> mapEntitiesToResponses(List<ENTITY> objects) {
        return objects.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }
    
    public abstract RESPONSE mapInterfaceToResponse(INTERFACEOBJECT object);

    public List<RESPONSE> mapInterfaceToResponses(List<INTERFACEOBJECT> objects) {
        return objects.stream().map(this::mapInterfaceToResponse).collect(Collectors.toList());
    }
}
