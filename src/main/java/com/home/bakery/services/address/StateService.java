package com.home.bakery.services.address;

public interface StateService {
    public void addState(String state);
    public void addStateWithDistrict(String state, Long districtId);
}
